package io.athletex.model.nfl

import kotlinx.serialization.Serializable

@Serializable
data class NFLStatsResponse(
    val stats: List<NFLPlayer>
)