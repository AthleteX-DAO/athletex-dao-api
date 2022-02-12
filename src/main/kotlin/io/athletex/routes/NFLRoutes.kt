package io.athletex.routes

import io.athletex.model.NFLPlayer
import io.athletex.model.PlayerStatsResponse
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

fun Application.nflPlayers(nflPlayerService: NFLPlayerService) {
    routing {
        route("/nfl") {
            get("/players") {
                val team: String = call.request.queryParameters["team"] ?: ""
                val position: String = call.request.queryParameters["position"] ?: ""
                try {
                    when {
                        team.isNotBlank() && position.isBlank() -> {
                            call.respond(HttpStatusCode.OK, nflPlayerService.getPlayersByTeam(team))
                        }
                        team.isBlank() && position.isNotBlank() -> {
                            call.respond(HttpStatusCode.OK, nflPlayerService.getPlayersByPosition(position))
                        }
                        team.isNotBlank() && position.isNotBlank() -> {
                            call.respond(HttpStatusCode.OK, nflPlayerService.getPlayersOnTeamByPosition(position, team))
                        }
                        else -> {
                            val playerIds = call.receiveOrNull<PlayerIds>()
                            val players =
                                if (playerIds != null) nflPlayerService.getPlayersById(playerIds)
                                else nflPlayerService.getAllPlayers()
                            call.respond(HttpStatusCode.OK, players)
                        }
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "${e.message}")
                }
            }
            
            get("/players/{id}") {
                try {
                    val playerId: Int = call.parameters["id"]!!.toInt()
                    call.respond(HttpStatusCode.OK, nflPlayerService.getPlayerById(playerId))
                }catch (e: NullPointerException) {
                    call.respond(HttpStatusCode.NotFound, "Player by this id is not found")
                }catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "${e.message}")
                }
            }

            get("/players/{id}/history") {
                try {
                    val playerId: Int = call.parameters["id"]!!.toInt()
                    call.respond(HttpStatusCode.OK, nflPlayerService.getPlayerHistory(playerId))
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
