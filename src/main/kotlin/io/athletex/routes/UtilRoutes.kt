package io.athletex.routes

import io.athletex.client.apis.util.models.RpcRequest
import io.athletex.client.apis.util.postSxRpcNode
import io.athletex.model.nfl.NFLStatsResponse
import io.athletex.routes.payloads.PlayerIds
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.serialization.json.Json

fun Application.utilRoutes() {
    routing {
        route("sx") {
            post("/rpc") {
                try {
                    val rpcRequest = call.receiveText()
                    val formattedRequest = rpcRequest.replace(" ", "").replace("\"latest\"", "").replace("},", "}")
                    val objReq = Json.decodeFromString(RpcRequest.serializer(), formattedRequest)
                    val response = postSxRpcNode(objReq)
                    call.respond(HttpStatusCode.OK, response)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "${e.message}")
                }
            }
        }
    }
}
