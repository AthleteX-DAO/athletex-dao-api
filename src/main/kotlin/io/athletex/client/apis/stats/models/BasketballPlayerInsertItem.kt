package io.athletex.client.apis.stats.models

data class BasketballPlayerInsertItem(
    val id: Int?,
    val name: String?,
    val price: Double?,
    val points: Double?,
    val threePointersMade: Double?,
    val fieldGoalsAttempted: Double?,
    val fieldGoalsMade: Double?,
    val freeThrowsAttempted: Double?,
    val freeThrowsMade: Double?,
    val rebounds: Double?,
    val assists: Double?,
    val steals: Double?,
    val blocks: Double?,
    val turnovers: Double?,
    val minutesPlayed: Int?,
)