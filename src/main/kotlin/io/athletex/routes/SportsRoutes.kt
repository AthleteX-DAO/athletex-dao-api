package io.athletex.routes

import io.athletex.services.PlayerService
import io.ktor.application.*
import io.ktor.routing.*

fun Application.sportsRoutes(playerService: PlayerService, endpoint: SportEndpoint) {
    routing {
        route(endpoint.value) {
            getPlayersByFilters(playerService)

            getPlayersByMultipleIds(playerService)

            getPlayerById(playerService)

            getPlayerHistory(playerService)

            getStatsFeedForPlayerById(playerService) { encodePlayerToJson(it) }
        }
    }
}
