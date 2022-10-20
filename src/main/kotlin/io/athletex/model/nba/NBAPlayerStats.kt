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
                        stat_history = stats
                    )
                }
                stats.add(
                    Stats(
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
        val price: Double,
        val timestamp: String,
    )
}