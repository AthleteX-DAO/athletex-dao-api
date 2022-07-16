package io.athletex.client

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*

object Client {

    val httpClient =  HttpClient(CIO) {

        install(ContentNegotiation){
            json()
        }

        install(Logging){
            logger = object : Logger {
                override fun log(message: String) {
                    println("Network Message log: $message")
                }
            }
            level = LogLevel.ALL
        }
        install(HttpTimeout){
            socketTimeoutMillis = 30_000
            requestTimeoutMillis = 30_000
            connectTimeoutMillis = 30_000
        }
        defaultRequest {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }
}