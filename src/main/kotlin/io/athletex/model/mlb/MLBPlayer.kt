package io.athletex.model.mlb

import io.athletex.model.Player
import kotlinx.serialization.Serializable
import java.sql.ResultSet

@Serializable
data class MLBPlayer(
    override val id: Int,
    override val name: String,
    val team: String,
    val position: String,
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
    override val price: Double,
    override val timestamp: String,
): Player() {
    companion object {
        fun parseSQL(row: ResultSet): MLBPlayer{
            return MLBPlayer(
                id = row.getInt(MLBPlayer::id.name),
                name = row.getString(MLBPlayer::name.name),
                team = row.getString(MLBPlayer::team.name),
                position = row.getString(MLBPlayer::position.name),
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
                saves = row.getDouble(MLBPlayer::inningsPlayed.name),
                strikeOuts = row.getDouble(MLBPlayer::strikeOuts.name),
                stolenBases = row.getDouble(MLBPlayer::stolenBases.name),
                plateAppearances = row.getDouble(MLBPlayer::plateAppearances.name),
                weightedOnBasePercentage = row.getDouble(MLBPlayer::weightedOnBasePercentage.name),
                price = row.getDouble(MLBPlayer::price.name),
                timestamp = row.getString(MLBPlayer::timestamp.name)
            )
        }
    }
}
