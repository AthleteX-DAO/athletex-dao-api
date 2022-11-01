package io.athletex.model.nba

import kotlinx.serialization.Serializable

@Serializable
data class NBAStatsResponse(
    val stats: List<NBAPlayer>
)