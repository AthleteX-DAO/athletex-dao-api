package io.athletex.model.mlb

import java.sql.ResultSet

data class MLBPlayer(
    val id: Int,
    val name: String,
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
    val pitchingHits: Double,
    val pitchingRuns: Double,
    val stolenBases: Double,
    val plateAppearances: Double,
    val weightedOnBasePercentage: Double,
    val igWeightedOnBasePercentage: Double,
    val price: Double,
) {
    companion object {
        fun parseSQL(row: ResultSet): MLBPlayer{
            return MLBPlayer(
                id = row.getInt("id"),
                name = row.getString("name"),
                team = row.getString("team"),
                position = row.getString("position"),
                started = row.getDouble("started"),
                games = row.getDouble("games"),
                atBats = row.getDouble("atBats"),
                runs = row.getDouble("runs"),
                singles = row.getDouble("singles"),
                triples = row.getDouble("triples"),
                homeRuns = row.getDouble("homeRuns"),
                inningsPlayed = row.getDouble("inningsPlayed"),
                battingAverage = row.getDouble("battingAverage"),
                outs = row.getDouble("outs"),
                walks = row.getDouble("walks"),
                errors = row.getDouble("errors"),
                saves = row.getDouble("inningsPlayed"),
                strikeOuts = row.getDouble("strikeOuts"),
                pitchingHits = row.getDouble("pitchingHits"),
                pitchingRuns = row.getDouble("pitchingRuns"),
                stolenBases = row.getDouble("stolenBases"),
                plateAppearances = row.getDouble("plateAppearances"),
                weightedOnBasePercentage = row.getDouble("weightedOnBasePercentage"),
                igWeightedOnBasePercentage = row.getDouble("igWeightedOnBasePercentage"),
                price = row.getDouble("price"),
            )
        }
    }
}
