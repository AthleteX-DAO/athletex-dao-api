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
    val passingTouchDowns: Double,
    val reception: Double,
    val receiveYards: Double,
    val receiveTouch: Double,
    val rushingTouch: Double,
    val rushingYards: Double,
    val passingInterceptions: Double,
    val OffensiveSnapsPlayed: Double,
    val DefensiveSnapsPlayed: Double,
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
                passingTouchDowns = row.getDouble(NFLPlayers.passingTouchdowns.name),
                reception = row.getDouble(NFLPlayers.reception.name),
                receiveYards = row.getDouble(NFLPlayers.receiveYards.name),
                receiveTouch = row.getDouble(NFLPlayers.receiveTouch.name),
                rushingTouch = row.getDouble(NFLPlayers.rushingTouchdowns.name),
                rushingYards = row.getDouble(NFLPlayers.rushingYards.name),
                passingInterceptions = row.getDouble(NFLPlayers.passingInterceptions.name),
                OffensiveSnapsPlayed = row.getDouble(NFLPlayers.offensiveSnapsPlayed.name),
                DefensiveSnapsPlayed = row.getDouble(NFLPlayers.defensiveSnapsPlayed.name),
                fumblesLost = row.getDouble(NFLPlayers.fumblesLost.name),
                price = row.getDouble(NFLPlayers.price.name),
                timestamp = row.getString(NFLPlayers.timestamp.name),
            )
        }
    }
}