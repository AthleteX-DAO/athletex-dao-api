package io.athletex.client

import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


object QuestApi {
    suspend fun getNFLPlayers(): HttpStatement {
        return Client.httpClient.use {
            it.get("http://139.99.74.201:9000/exec"){
                parameter("limit", "0,1000")
                parameter("count", "true")
                parameter("query", "SELECT * from nfl")
            }
        }
    }
    suspend fun <T> apiRequest(block: () -> T): T =
        withContext(Dispatchers.IO) {
            block()
        }
}