package io.athletex.plugins

import io.athletex.routes.mlbRoutes
import io.athletex.routes.nflRoutes
import io.athletex.services.MLBPlayerService
import io.athletex.services.NFLPlayerService
import io.ktor.application.*

fun Application.configureRouting() {
    nflRoutes(NFLPlayerService())
    mlbRoutes(MLBPlayerService())
}
