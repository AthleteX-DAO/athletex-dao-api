package io.athletex.routes

import io.athletex.services.MLBPlayerService
import io.ktor.application.*
import io.ktor.routing.*

fun Application.mRoutes(playerService: MLBPlayerService) {
    routing {
        route("/mlb") {
            getPlayersByFilters(playerService)
        }
    }
}