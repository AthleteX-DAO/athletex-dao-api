package io.athletex.model.nfl

import io.athletex.db.models.NFLPlayers
import io.athletex.model.Player
import kotlinx.serialization.Serializable
import java.sql.ResultSet

@Serializable
data class NFLPlayer(
    override val name: String,
    override val id: Int,
    val team: String,
    val position: String,
    val passingYards: Double,
    val passingTouchdowns: Double,
    val reception: Double,
    val receivingYards: Double,
    val receivingTouchdowns: Double,
    val rushingTouchdowns: Double,
    val rushingYards: Double,
    val passingInterceptions: Double,
    val offensiveSnapsPlayed: Double,
    val defensiveSnapsPlayed: Double,
    val fumblesLost: Double,
    override val price: Double,
    override val timestamp: String,
): Player() {
    companion object {
        fun parseSQL(row: ResultSet): NFLPlayer {
            return NFLPlayer(
                name = row.getString(NFLPlayers.name.name),
                id = row.getString(NFLPlayers.id.name).toInt(),
                team = row.getString(NFLPlayers.team.name),
                position = row.getString(NFLPlayers.position.name),
                passingYards = row.getDouble(NFLPlayers.passingYards.name),
                passingTouchdowns = row.getDouble(NFLPlayers.passingTouchdowns.name),
                reception = row.getDouble(NFLPlayers.reception.name),
                receivingYards = row.getDouble(NFLPlayers.receivingYards.name),
                receivingTouchdowns = row.getDouble(NFLPlayers.receivingTouchdowns.name),
                rushingYards = row.getDouble(NFLPlayers.rushingYards.name),
                rushingTouchdowns = row.getDouble(NFLPlayers.rushingTouchdowns.name),
                passingInterceptions = row.getDouble(NFLPlayers.passingInterceptions.name),
                offensiveSnapsPlayed = row.getDouble(NFLPlayers.offensiveSnapsPlayed.name),
                defensiveSnapsPlayed = row.getDouble(NFLPlayers.defensiveSnapsPlayed.name),
                fumblesLost = row.getDouble(NFLPlayers.fumblesLost.name),
                price = row.getDouble(NFLPlayers.price.name),
                timestamp = row.getString(NFLPlayers.timestamp.name),
            )
        }
    }
}