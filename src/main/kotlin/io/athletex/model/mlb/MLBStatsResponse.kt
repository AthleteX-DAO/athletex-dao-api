package io.athletex.model.mlb

import kotlinx.serialization.Serializable

@Serializable
data class MLBStatsResponse(
    val stats: List<MLBPlayer>
)