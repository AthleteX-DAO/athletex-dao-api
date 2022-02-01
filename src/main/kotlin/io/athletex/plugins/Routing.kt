package io.athletex.plugins

import io.athletex.routes.nflPlayers
import io.athletex.services.NFLPlayerService
import io.ktor.application.*

fun Application.configureRouting(nflPlayerService: NFLPlayerService) {
    nflPlayers(nflPlayerService)
}
