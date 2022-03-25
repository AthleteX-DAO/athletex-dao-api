package io.athletex.routes

import io.athletex.model.Player
import io.athletex.routes.payloads.PlayerIds
import io.athletex.services.PlayerService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException

internal fun Route.getPlayersByFilters(playerService: PlayerService) {
    get("/players") {
        val team: String = call.request.queryParameters["team"] ?: ""
        val position: String = call.request.queryParameters["position"] ?: ""
        try {
            when {
                team.isNotBlank() && position.isBlank() -> {
                    call.respond(HttpStatusCode.OK, playerService.getPlayersByTeam(team))
                }
                team.isBlank() && position.isNotBlank() -> {
                    call.respond(HttpStatusCode.OK, playerService.getPlayersByPosition(position))
                }
                team.isNotBlank() && position.isNotBlank() -> {
                    call.respond(HttpStatusCode.OK, playerService.getPlayersOnTeamByPosition(position, team))
                }
                else -> {
                    val playerIds = call.receiveOrNull<PlayerIds>()
                    val players =
                        if (playerIds != null) playerService.getPlayersById(playerIds)
                        else playerService.getAllPlayers()
                    call.respond(HttpStatusCode.OK, players)
                }
            }
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, "${e.message}")
        }
    }
}

internal fun Route.getPlayersByMultipleIds(playerService: PlayerService) {
    post("/players") {
        try {
            val playerIds = call.receiveOrNull<PlayerIds>()
            val players =
                if (playerIds != null) playerService.getPlayersById(playerIds)
                else playerService.getAllPlayers()
            call.respond(HttpStatusCode.OK, players)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, "${e.message}")
        }
    }
}

internal fun Route.getPlayerById(playerService: PlayerService) {
    get("/players/{id}") {
        try {
            val targetDay: String? = call.request.queryParameters["at_day"]
            val playerId: Int = call.parameters["id"]!!.toInt()
            call.respond(HttpStatusCode.OK, playerService.getPlayerById(playerId, targetDay))
        } catch (e: NullPointerException) {
            call.respond(HttpStatusCode.NotFound, "Player by this id is not found")
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, "${e.message}")
        }
    }
}

internal fun Route.getPlayerHistory(playerService: PlayerService) {
    get("/players/{id}/history") {
        try {
            val from: String? = call.request.queryParameters["from"]
            val until: String? = call.request.queryParameters["until"]
            val playerId: Int = call.parameters["id"]!!.toInt()
            call.respond(HttpStatusCode.OK, playerService.getPlayerHistory(playerId, from, until))
        } catch (e: NullPointerException) {
            call.respond(HttpStatusCode.NotFound, "Player by this id is not found")
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, "${e.message}")
        }
    }
}

internal fun Route.getStatsFeedForPlayerById(
    playerService: PlayerService,
    encodePlayerToString: (Player) -> String
) {
    webSocket("/players/{id}/feed") {
        try {
            val playerId: Int = call.parameters["id"]!!.toInt()
            playerService.getStatsFeedByPlayer(playerId).collect { player ->
                send(encodePlayerToString(player))
            }
        } catch (e: ClosedReceiveChannelException) {
            println("onClose ${closeReason.await()}")
        } catch (e: Throwable) {
            println("onError ${closeReason.await()}")
            e.printStackTrace()
        }
    }
}

