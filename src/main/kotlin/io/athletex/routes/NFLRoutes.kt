package io.athletex.routes

import io.athletex.model.nfl.NFLStatsResponse
import io.athletex.routes.SportEndpoint.NFL
import io.athletex.services.NFLPlayerService
import io.ktor.application.*
import io.ktor.http.cio.websocket.*
import io.ktor.routing.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.serialization.json.Json

fun Application.nflFeedRoutes(nflPlayerService: NFLPlayerService) {
    routing {
        route(NFL.value) {
            webSocket("/players/feed") {
                try {
                    nflPlayerService.getStatsFeed().collect { players ->
                        send(
                            Json.encodeToString(
                                NFLStatsResponse.serializer(),
                                NFLStatsResponse(players)
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
