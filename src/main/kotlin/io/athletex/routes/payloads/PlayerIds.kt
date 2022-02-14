package io.athletex.routes.payloads

import kotlinx.serialization.Serializable

@Serializable
data class PlayerIds(
    val ids: List<Int>
)
