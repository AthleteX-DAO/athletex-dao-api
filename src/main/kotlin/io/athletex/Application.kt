package io.athletex

import io.athletex.db.DatabaseFactory
import io.athletex.plugins.configureRouting
import io.athletex.plugins.configureSecurity
import io.athletex.plugins.configureSerialization
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import io.ktor.websocket.*
import org.slf4j.event.Level
import java.time.Duration

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }
    install(ForwardedHeaderSupport)
    install(DefaultHeaders)
    install(CORS){
        method(HttpMethod.Options)
        method(HttpMethod.Get)
        method(HttpMethod.Post)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.AccessControlAllowHeaders)
        header(HttpHeaders.ContentType)
        header(HttpHeaders.AccessControlAllowOrigin)
        anyHost()
    }
    install(CallLogging) {
        level = Level.ERROR
    }
    DatabaseFactory.init()
    configureRouting()
    configureSerialization()
    configureSecurity()
}
