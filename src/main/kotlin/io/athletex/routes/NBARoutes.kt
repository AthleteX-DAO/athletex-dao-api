package io.athletex.routes

import io.athletex.model.nba.NBAStatsResponse
import io.athletex.routes.SportEndpoint.NBA
import io.athletex.services.NBAPlayerService
import io.ktor.server.application.*
import io.ktor.websocket.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.serialization.json.Json

fun Application.nbaFeedRoutes(nbaPlayerService: NBAPlayerService) {
    routing {
        route(NBA.value) {
            webSocket("/players/feed") {
                try {
                    nbaPlayerService.getStatsFeed().collect { players ->
                        send(
                            Json.encodeToString(
                                NBAStatsResponse.serializer(),
                                NBAStatsResponse(players)
                            )
                        )
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
