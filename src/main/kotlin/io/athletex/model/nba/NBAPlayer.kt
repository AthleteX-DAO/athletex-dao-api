package io.athletex.model.nba

import io.athletex.model.Player
import kotlinx.serialization.Serializable
import java.sql.ResultSet

@Serializable
data class NBAPlayer(
    override val id: Int,
    override val name: String,
    val team: String,
    val position: String,
    val points: Double,
    val threePointersMade: Double,
    val fieldGoalsAttempted: Double,
    val fieldGoalsMade: Double,
    val freeThrowsAttempted: Double,
    val freeThrowsMade: Double,
    val rebounds: Double,
    val assists: Double,
    val steals: Double,
    val blocks: Double,
    val turnovers: Double,
    val minutesPlayed: Double,
    override val price: Double,
    override val timestamp: String,
): Player() {
    companion object {
        fun parseSQL(row: ResultSet): NBAPlayer{
            return NBAPlayer(
                id = row.getInt(NBAPlayer::id.name),
                name = row.getString(NBAPlayer::name.name),
                team = row.getString(NBAPlayer::team.name),
                position = row.getString(NBAPlayer::position.name),
                points = row.getDouble(NBAPlayer::points.name),
                threePointersMade = row.getDouble(NBAPlayer::threePointersMade.name),
                fieldGoalsAttempted = row.getDouble(NBAPlayer::fieldGoalsAttempted.name),
                fieldGoalsMade = row.getDouble(NBAPlayer::fieldGoalsMade.name),
                freeThrowsAttempted = row.getDouble(NBAPlayer::freeThrowsAttempted.name),
                freeThrowsMade = row.getDouble(NBAPlayer::freeThrowsMade.name),
                rebounds = row.getDouble(NBAPlayer::rebounds.name),
                assists = row.getDouble(NBAPlayer::assists.name),
                blocks = row.getDouble(NBAPlayer::blocks.name),
                steals = row.getDouble(NBAPlayer::steals.name),
                turnovers = row.getDouble(NBAPlayer::turnovers.name),
                minutesPlayed = row.getDouble(NBAPlayer::minutesPlayed.name),
                price = row.getDouble(NBAPlayer::price.name),
                timestamp = row.getString(NBAPlayer::timestamp.name)
            )
        }
    }
}

