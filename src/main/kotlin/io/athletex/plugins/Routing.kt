package io.athletex.plugins

import io.athletex.routes.SportEndpoint.MLB
import io.athletex.routes.SportEndpoint.NFL
import io.athletex.routes.mlbFeedRoutes
import io.athletex.routes.nflFeedRoutes
import io.athletex.routes.sportsRoutes
import io.athletex.routes.utilRoutes
import io.athletex.services.MLBPlayerService
import io.athletex.services.NFLPlayerService
import io.ktor.server.application.*

fun Application.configureRouting() {
    val nflPlayerService = NFLPlayerService()
    val mlbPlayerService = MLBPlayerService()
    sportsRoutes(mlbPlayerService, MLB)
    sportsRoutes(nflPlayerService, NFL)
    nflFeedRoutes(nflPlayerService)
    mlbFeedRoutes(mlbPlayerService)
    utilRoutes()
}
