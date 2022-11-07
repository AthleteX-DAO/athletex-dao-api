package io.athletex.model.nba

import io.athletex.model.Player
import kotlinx.serialization.Serializable
import java.sql.ResultSet

@Serializable
data class NBAPlayer(
    override val id: Int,
    override val name: String,
    val points: Double,
    val rebounds: Double,
    val assists: Double,
    val blocks: Double,
    val steals: Double,
    val minutesPlayed: Double,
    override val price: Double,
    override val timestamp: String,
): Player() {
    companion object {
        fun parseSQL(row: ResultSet): NBAPlayer{
            return NBAPlayer(
                id = row.getInt(NBAPlayer::id.name),
                name = row.getString(NBAPlayer::name.name),
                points = row.getDouble(NBAPlayer::points.name),
                rebounds = row.getDouble(NBAPlayer::rebounds.name),
                assists = row.getDouble(NBAPlayer::assists.name),
                blocks = row.getDouble(NBAPlayer::blocks.name),
                steals = row.getDouble(NBAPlayer::steals.name),
                minutesPlayed = row.getDouble(NBAPlayer::minutesPlayed.name),
                price = row.getDouble(NBAPlayer::price.name),
                timestamp = row.getString(NBAPlayer::timestamp.name)
            )
        }
    }
}
