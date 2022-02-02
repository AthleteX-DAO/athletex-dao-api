package io.athletex.model

import kotlinx.serialization.Serializable

@Serializable
data class PlayerStatsResponse(
    val stats: List<NFLPlayer>
)