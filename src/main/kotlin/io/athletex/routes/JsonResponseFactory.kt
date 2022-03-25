package io.athletex.routes

import io.athletex.model.Player
import io.athletex.model.mlb.MLBPlayer
import io.athletex.model.nfl.NFLPlayer
import kotlinx.serialization.json.Json

fun encodePlayerToJson(player: Player): String {
    return when (player) {
        is NFLPlayer -> Json.encodeToString(NFLPlayer.serializer(), player)
        is MLBPlayer -> Json.encodeToString(MLBPlayer.serializer(), player)

        else -> throw IllegalArgumentException("Must be of type Player")
    }
}
