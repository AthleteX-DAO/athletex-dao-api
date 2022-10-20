package io.athletex.model.nba

import io.athletex.model.Player
import kotlinx.serialization.Serializable
import java.sql.ResultSet

@Serializable
data class NBAPlayer(
    override val id: Int,
    override val name: String,
    override val price: Double,
    override val timestamp: String,
): Player() {
    companion object {
        fun parseSQL(row: ResultSet): NBAPlayer{
            return NBAPlayer(
                id = row.getInt(NBAPlayer::id.name),
                name = row.getString(NBAPlayer::name.name),
                price = row.getDouble(NBAPlayer::price.name),
                timestamp = row.getString(NBAPlayer::timestamp.name)
            )
        }
    }
}
