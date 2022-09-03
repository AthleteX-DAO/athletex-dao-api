package io.athletex.client.apis.util

import io.athletex.client.Client
import io.athletex.client.apis.util.models.RpcRequest
import io.athletex.client.apis.util.models.RpcResponse
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

suspend fun postSxRpcNode(request: RpcRequest): RpcResponse {

    val response = Client.httpClient.request("https://rpc.sx.technology/") {
        method = HttpMethod.Post
        setBody(request)
        headers.append("Content-Type", "application/json")
        headers.append("Accept", "*/*")
    }
    return response.body()
}