package io.athletex.routes

import io.athletex.services.PlayerService
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.sportsRoutes(playerService: PlayerService, endpoint: SportEndpoint) {
    routing {
        route(endpoint.value) {
            getPlayersByFilters(playerService)

            getPlayersByMultipleIds(playerService)

            getPlayerById(playerService)

            getPlayerHistory(playerService)

            getPlayersHistories(playerService)

            getStatsFeedForPlayerById(playerService) { encodePlayerToJson(it) }
        }
    }
}
