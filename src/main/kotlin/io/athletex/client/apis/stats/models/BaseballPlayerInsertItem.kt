package io.athletex.client.apis.stats.models

data class BaseballPlayerInsertItem(
    val id: Int,
    val name: String,
    val team: String,
    val position: String,
    val started: Double,
    val games: Double,
    val atBats: Double,
    val runs: Double,
    val singles: Double,
    val doubles: Double,
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
)