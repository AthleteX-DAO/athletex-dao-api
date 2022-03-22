package io.athletex.routes

import io.athletex.routes.payloads.PlayerIds
import io.athletex.services.PlayerService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

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
