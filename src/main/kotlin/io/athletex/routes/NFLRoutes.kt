package io.athletex.routes

import io.athletex.model.nfl.NFLPlayer
import io.athletex.model.nfl.PlayerStatsResponse
import io.athletex.routes.payloads.PlayerIds
import io.athletex.services.NFLPlayerService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.serialization.json.Json

fun Application.nflRoutes(nflPlayerService: NFLPlayerService) {
    routing {
        route("/nfl") {
            getPlayersByFilters(nflPlayerService)

            post("/players") {
                try {
                    val playerIds = call.receiveOrNull<PlayerIds>()
                    val players =
                        if (playerIds != null) nflPlayerService.getPlayersById(playerIds)
                        else nflPlayerService.getAllPlayers()
                    call.respond(HttpStatusCode.OK, players)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "${e.message}")
                }
            }

            get("/players/{id}") {
                try {
                    val targetDay: String? = call.request.queryParameters["at_day"]
                    val playerId: Int = call.parameters["id"]!!.toInt()
                    call.respond(HttpStatusCode.OK, nflPlayerService.getPlayerById(playerId, targetDay))
                }catch (e: NullPointerException) {
                    call.respond(HttpStatusCode.NotFound, "Player by this id is not found")
                }catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "${e.message}")
                }
            }

            get("/players/{id}/history") {
                try {
                    val from: String? = call.request.queryParameters["from"]
                    val until: String? = call.request.queryParameters["until"]
                    val playerId: Int = call.parameters["id"]!!.toInt()
                    call.respond(HttpStatusCode.OK, nflPlayerService.getPlayerHistory(playerId, from, until))
                }catch (e: NullPointerException) {
                    call.respond(HttpStatusCode.NotFound, "Player by this id is not found")
                }catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "${e.message}")
                }
            }

            webSocket("/players/feed"){
                try {
                    nflPlayerService.getStatsFeed().collect { players ->
                        send(Json.encodeToString(PlayerStatsResponse.serializer(), PlayerStatsResponse(players)))
                    }
                } catch (e: ClosedReceiveChannelException) {
                    println("onClose ${closeReason.await()}")
                } catch (e: Throwable) {
                    println("onError ${closeReason.await()}")
                    e.printStackTrace()
                }
            }

            webSocket("/players/{id}/feed"){
                try {
                    val playerId: Int = call.parameters["id"]!!.toInt()
                    nflPlayerService.getStatsFeedByPlayer(playerId).collect { player ->
                        send(Json.encodeToString(NFLPlayer.serializer(), player))
                    }
                } catch (e: ClosedReceiveChannelException) {
                    println("onClose ${closeReason.await()}")
                } catch (e: Throwable) {
                    println("onError ${closeReason.await()}")
                    e.printStackTrace()
                }
            }
        }
    }
}

