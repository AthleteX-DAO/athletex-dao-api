package io.athletex.routes

import io.athletex.model.Player
import io.athletex.routes.payloads.PlayerIds
import io.athletex.services.PlayerService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
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
                    val players = if (playerIds != null) playerService.getPlayersById(playerIds)
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

internal fun Route.getPlayerPriceHistory(playerService: PlayerService) {
    get("/players/{id}/history/price") {
        try {
            val from: String? = call.request.queryParameters["from"]
            val until: String? = call.request.queryParameters["until"]
            val interval: String? = call.request.queryParameters["interval"]
            val playerId: Int = call.parameters["id"]!!.toInt()
            call.respond(
                HttpStatusCode.OK,
                playerService.getPlayerPriceHistory(playerId, from, until, (interval ?: "2h"))
            )
        } catch (e: NullPointerException) {
            call.respond(HttpStatusCode.NotFound, "Player by this id is not found")
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, "${e.message}")
        }
    }
}

internal fun Route.getPlayersHistories(playerService: PlayerService) {
    post("/players/history") {
        try {
            val from: String? = call.request.queryParameters["from"]
            val until: String? = call.request.queryParameters["until"]
            val playerIds = call.receiveOrNull<PlayerIds>()
            if (playerIds != null) {
                call.respond(HttpStatusCode.OK, playerService.getPlayersHistories(playerIds, from, until))
            } else {
                call.respond(HttpStatusCode.BadRequest, "ids of athletes were missing")
            }
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, "${e.message}")
        }
    }
}

internal fun Route.updateDB(playerService: PlayerService) {
    get("/players/update") {
        try {
            playerService.updateDatabase()
            call.respond(HttpStatusCode.OK, "DB updated")
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
