package io.athletex.routes

import io.athletex.model.mlb.MLBStatsResponse
import io.athletex.routes.SportEndpoint.MLB
import io.athletex.services.MLBPlayerService
import io.ktor.application.*
import io.ktor.http.cio.websocket.*
import io.ktor.routing.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.serialization.json.Json

fun Application.mlbFeedRoutes(mlbPlayerService: MLBPlayerService) {
    routing {
        route(MLB.value) {
            webSocket("/players/feed") {
                try {
                    mlbPlayerService.getStatsFeed().collect { players ->
                        send(
                            Json.encodeToString(
                                MLBStatsResponse.serializer(),
                                MLBStatsResponse(players)
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
