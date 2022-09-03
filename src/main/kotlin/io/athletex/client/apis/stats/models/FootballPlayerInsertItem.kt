package io.athletex.client.apis.stats.models

data class FootballPlayerInsertItem(
    val name: String,
    val id: Int,
    val team: String,
    val position: String,
    val passingYards: Double?,
    val passingTouchDowns: Double?,
    val reception: Double?,
    val receiveYards: Double?,
    val receiveTouch: Double?,
    val rushingYards: Double?,
    val rushingTouch: Double?,
    val OffensiveSnapsPlayed: Double?,
    val DefensiveSnapsPlayed: Double?,
    val price: Double?,
)