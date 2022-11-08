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
    val blocks: Double,
    val steals: Double,
    val turnovers: Double,
    val minutesPlayed: Int,
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
                points = row.getString(NBAPlayer::points.name),
                threePointersMade = row.getString(NBAPlayer::threePointersMade.name),
                fieldGoalsAttempted = row.getString(NBAPlayer::fieldGoalsAttempted.name),
                fieldGoalsMade = row.getString(NBAPlayer::fieldGoalsMade.name),
                freeThrowsAttempted = row.getString(NBAPlayer::freeThrowsAttempted.name),
                freeThrowsMade = row.getString(NBAPlayer::freeThrowsMade.name),
                rebounds = row.getString(NBAPlayer::rebounds.name),
                assists = row.getString(NBAPlayer::assists.name),
                steals = row.getString(NBAPlayer::steals.name),
                blocks = row.getString(NBAPlayer::blocks.name),
                turnovers = row.getString(NBAPlayer::turnovers.name),
                minutesPlayed = row.getString(NBAPlayer::minutesPlayed.name),
                price = row.getDouble(NBAPlayer::price.name),
                timestamp = row.getString(NBAPlayer::timestamp.name)
            )
        }
    }
}
