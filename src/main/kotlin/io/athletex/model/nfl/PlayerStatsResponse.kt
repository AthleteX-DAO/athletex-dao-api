package io.athletex.model.nfl

import kotlinx.serialization.Serializable

@Serializable
data class PlayerStatsResponse(
    val stats: List<NFLPlayer>
)