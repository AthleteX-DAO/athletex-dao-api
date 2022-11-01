package io.athletex.model.nba

import io.athletex.model.Player
import io.athletex.model.PlayerStats
import io.athletex.model.wasRecordedDuringBadEntryTime
import kotlinx.serialization.Serializable
import java.sql.ResultSet

@Serializable
data class NBAPlayerStats (
    val name: String,
    val id: Int,
    val team: String,
    val position: String,
    val stat_history: List<Stats>
): PlayerStats() {
    companion object {

        fun parseStatHistory(row: ResultSet): NBAPlayerStats {
            val stats: MutableList<Stats> = mutableListOf()
            var nbaPlayerStats: NBAPlayerStats? = null

            while (row.next()) {
                if(wasRecordedDuringBadEntryTime(row.getString(Player::timestamp.name))) break
                if (row.isFirst) {
                    nbaPlayerStats = NBAPlayerStats(
                        name = row.getString(NBAPlayer::name.name),
                        id = row.getInt(NBAPlayer::id.name),
                        team = row.getString(NBAPlayer::team.name),
                        position = row.getString(NBAPlayer::position.name),
                        stat_history = stats
                    )
                }
                stats.add(
                    Stats(
                        points = row.getString(NBAPlayer::points.name),
                        rebounds = row.getString(NBAPlayer::rebounds.name),
                        assists = row.getString(NBAPlayer::assists.name),
                        blocks = row.getString(NBAPlayer::blocks.name),
                        steals = row.getString(NBAPlayer::steals.name),
                        minutesPlayed = row.getString(NBAPlayer::minutesPlayed.name),
                        price = row.getDouble(NBAPlayer::price.name),
                        timestamp = row.getString(NBAPlayer::timestamp.name)
                    )
                )
            }
            return nbaPlayerStats!!
        }
    }

    @Serializable
    data class Stats(
        val points: Double,
        val rebounds: Double,
        val assists: Doulble,
        val blocks: Double,
        val steals: Double,
        val minutesPlayed: Double,
        val price: Double,
        val timestamp: String,
    )
}