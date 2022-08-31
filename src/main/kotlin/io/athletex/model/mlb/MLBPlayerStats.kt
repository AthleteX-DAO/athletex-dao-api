package io.athletex.model.mlb

import io.athletex.model.Player
import io.athletex.model.PlayerStats
import io.athletex.model.wasRecordedDuringBadEntryTime
import kotlinx.serialization.Serializable
import java.sql.ResultSet

@Serializable
data class MLBPlayerStats (
    val name: String,
    val id: Int,
    val team: String,
    val position: String,
    val stat_history: List<Stats>
): PlayerStats() {
    companion object {

        fun parseStatHistory(row: ResultSet): MLBPlayerStats {
            val stats: MutableList<Stats> = mutableListOf()
            var mlbPlayerStats: MLBPlayerStats? = null

            while (row.next()) {
                if(wasRecordedDuringBadEntryTime(row.getString(Player::timestamp.name))) break
                if (row.isFirst) {
                    mlbPlayerStats = MLBPlayerStats(
                        name = row.getString(MLBPlayer::name.name),
                        id = row.getInt(MLBPlayer::id.name),
                        team = row.getString(MLBPlayer::team.name),
                        position = row.getString(MLBPlayer::position.name),
                        stat_history = stats
                    )
                }
                stats.add(
                    Stats(
                        started = row.getDouble(MLBPlayer::started.name),
                        games = row.getDouble(MLBPlayer::games.name),
                        atBats = row.getDouble(MLBPlayer::atBats.name),
                        runs = row.getDouble(MLBPlayer::runs.name),
                        singles = row.getDouble(MLBPlayer::singles.name),
                        triples = row.getDouble(MLBPlayer::triples.name),
                        homeRuns = row.getDouble(MLBPlayer::homeRuns.name),
                        inningsPlayed = row.getDouble(MLBPlayer::inningsPlayed.name),
                        battingAverage = row.getDouble(MLBPlayer::battingAverage.name),
                        outs = row.getDouble(MLBPlayer::outs.name),
                        walks = row.getDouble(MLBPlayer::walks.name),
                        errors = row.getDouble(MLBPlayer::errors.name),
                        saves = row.getDouble(MLBPlayer::saves.name),
                        strikeOuts = row.getDouble(MLBPlayer::strikeOuts.name),
                        stolenBases = row.getDouble(MLBPlayer::stolenBases.name),
                        plateAppearances = row.getDouble(MLBPlayer::plateAppearances.name),
                        weightedOnBasePercentage = row.getDouble(MLBPlayer::weightedOnBasePercentage.name),
                        price = row.getDouble(MLBPlayer::price.name),
                        timestamp = row.getString(MLBPlayer::timestamp.name)
                    )
                )
            }
            return mlbPlayerStats!!
        }
    }

    @Serializable
    data class Stats(
        val started: Double,
        val games: Double,
        val atBats: Double,
        val runs: Double,
        val singles: Double,
        val triples: Double,
        val homeRuns: Double,
        val inningsPlayed: Double,
        val battingAverage: Double,
        val outs: Double,
        val walks: Double,
        val errors: Double,
        val saves: Double,
        val strikeOuts: Double,
        val stolenBases: Double,
        val plateAppearances: Double,
        val weightedOnBasePercentage: Double,
        val price: Double,
        val timestamp: String,
    )
}