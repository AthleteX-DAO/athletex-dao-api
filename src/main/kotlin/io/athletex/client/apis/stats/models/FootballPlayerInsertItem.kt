package io.athletex.client.apis.stats.models

data class FootballPlayerInsertItem(
    val name: String,
    val id: Int,
    val team: String,
    val position: String,
    val passingYards: Double? = 0.0,
    val passingTouchdowns: Double? = 0.0,
    val reception: Double? = 0.0,
    val receivingYards: Double? = 0.0,
    val receivingTouchdowns: Double? = 0.0,
    val rushingYards: Double? = 0.0,
    val passingInterceptions: Double? = 0.0,
    val rushingTouchdowns: Double? = 0.0,
    val offensiveSnapsPlayed: Double? = 0.0,
    val defensiveSnapsPlayed: Double? = 0.0,
    val fumblesLost: Double? = 0.0,
    val price: Double? = 0.0,
)