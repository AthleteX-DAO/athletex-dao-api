package io.athletex.model

import kotlinx.serialization.Serializable

@Serializable
abstract class Player {
    abstract val id: Int
    abstract val name: String
    abstract val price: Double
    abstract val timestamp: String
}
