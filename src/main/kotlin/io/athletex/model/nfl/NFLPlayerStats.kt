package io.athletex.model.nfl

import io.athletex.db.models.NFLPlayers
import io.athletex.model.PlayerStats
import kotlinx.serialization.Serializable
import java.sql.ResultSet

@Serializable
data class NFLPlayerStats(
    val name: String,
    val id: Int,
    val team: String,
    val position: String,
    val stat_history: List<Stats>
): PlayerStats() {
    companion object {

        fun parseStatHistory(row: ResultSet): NFLPlayerStats {
            val stats: MutableList<Stats> = mutableListOf()
            var nflPlayerStats: NFLPlayerStats? = null

            while (row.next()) {
                if (row.isFirst) {
                    nflPlayerStats = NFLPlayerStats(
                        name = row.getString(NFLPlayers.name.name),
                        id = row.getString(NFLPlayers.id.name).toInt(),
                        team = row.getString(NFLPlayers.team.name),
                        position = row.getString(NFLPlayers.position.name),
                        stat_history = stats
                    )
                }
                stats.add(
                    Stats(
                        passingYards = row.getDouble(NFLPlayers.passingYards.name),
                        passingTouchDowns = row.getDouble(NFLPlayers.passingTouchdowns.name),
                        reception = row.getDouble(NFLPlayers.reception.name),
                        receivingYards = row.getDouble(NFLPlayers.receivingYards.name),
                        receivingTouchdowns = row.getDouble(NFLPlayers.receivingTouchdowns.name),
                        rushingYards = row.getDouble(NFLPlayers.rushingYards.name),
                        offensiveSnapsPlayed = row.getDouble(NFLPlayers.offensiveSnapsPlayed.name),
                        defensiveSnapsPlayed = row.getDouble(NFLPlayers.defensiveSnapsPlayed.name),
                        price = row.getDouble(NFLPlayers.price.name),
                        timestamp = row.getString(NFLPlayers.timestamp.name).toString(),
                    )
                )
            }
            return nflPlayerStats!!
        }
    }

    @Serializable
    data class Stats(
        val passingYards: Double,
        val passingTouchDowns: Double,
        val reception: Double,
        val receivingYards: Double,
        val receivingTouchdowns: Double,
        val rushingYards: Double,
        val offensiveSnapsPlayed: Double,
        val defensiveSnapsPlayed: Double,
        val price: Double,
        val timestamp: String,
    )
}