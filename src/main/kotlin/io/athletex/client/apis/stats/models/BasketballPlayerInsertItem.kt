package io.athletex.client.apis.stats.models

data class BasketballPlayerInsertItem(
    val id: Int?,
    val name: String?,
    val team: String?,
    val position: String?,
    val price: Double?,
    val timestamp: String?,
    val points: Double?,
    val rebounds: Double?,
    val assists: Double?,
    val blocks: Double?,
    val steals: Double?,
    val minutesPlayed: Int?,
)