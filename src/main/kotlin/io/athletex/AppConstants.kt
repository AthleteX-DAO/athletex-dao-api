package io.athletex

import com.typesafe.config.ConfigFactory
import io.ktor.server.config.*

enum class Sports {
    NFL,
    MLB,
}

val appConfig = HoconApplicationConfig(ConfigFactory.load())
