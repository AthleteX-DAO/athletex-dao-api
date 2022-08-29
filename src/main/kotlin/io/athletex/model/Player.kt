package io.athletex.model

import io.athletex.model.Player
import kotlinx.serialization.Serializable
import java.sql.ResultSet

@Serializable
abstract class Player {
    override val id: Int,
    override val name: String,
    val team: String,
    val position: String,
    
    val passingYards: Double,
    val rushingYards: Double,
    val receivingYards: Double,
    val rushingTouchdowns: Double,
    val receivingTouchdowns: Double,
    val passingTouchdowns: Double,
    val receptions: Double,
    val passingInterceptions: Double,
    val fumblesLost: Double,
    val offensiveSnapsPlayed: Double,
    val defensiveSnapsPlayed: Double,

    override val price: Double,
    override val timestamp: String,
}
