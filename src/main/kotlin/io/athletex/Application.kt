package io.athletex

import io.athletex.db.DatabaseFactory
import io.ktor.application.*
import io.athletex.plugins.*
import io.athletex.services.NFLPlayerService
import io.ktor.features.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.slf4j.event.Level

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    install(DefaultHeaders)
    install(CallLogging) {
        level = Level.DEBUG
    }
    DatabaseFactory.init()
    configureRouting(NFLPlayerService())
    configureSerialization()
    configureSecurity()
}
