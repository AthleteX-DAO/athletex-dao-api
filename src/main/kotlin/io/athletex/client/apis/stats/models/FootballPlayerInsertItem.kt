package io.athletex.client.apis.stats.models

data class FootballPlayerInsertItem(
    val name: String,
    val id: Int,
    val team: String,
    val position: String,
    val passingYards: Double?,
    val passingTouchDowns: Double?,
    val reception: Double?,
    val receivingYards: Double?,
    val receivingTouchdowns: Double?,
    val rushingYards: Double?,
    val rushingTouchdowns: Double?,
    val offensiveSnapsPlayed: Double?,
    val defensiveSnapsPlayed: Double?,
    val price: Double?,
)