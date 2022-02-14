package io.athletex.services

import io.athletex.model.NFLPlayer
import io.athletex.model.NFLPlayerStats
import io.athletex.routes.payloads.PlayerIds
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class NFLPlayerService {

    suspend fun getAllPlayers(): List<NFLPlayer> = newSuspendedTransaction {
        executeQueryOfNflPlayers(
            "SELECT * FROM nfl " +
                    "LATEST BY name " +
                    "ORDER by name"
        )
    }

    suspend fun getLatestPlayerStats(): List<NFLPlayer> = newSuspendedTransaction {
        executeQueryOfNflPlayers(
            "SELECT * FROM nfl " +
                    "LATEST BY name " +
                    "ORDER by timestamp DESC "
        )
    }

    suspend fun getPlayersByTeam(team: String): List<NFLPlayer> = newSuspendedTransaction {
        executeQueryOfNflPlayers(
            "SELECT * FROM nfl " +
                    "LATEST BY name " +
                    "WHERE team = '$team' " +
                    "ORDER by name"
        )
    }

    suspend fun getPlayersByPosition(position: String): List<NFLPlayer> = newSuspendedTransaction {
        executeQueryOfNflPlayers(
            "SELECT * FROM nfl " +
                    "LATEST BY name " +
                    "WHERE position = '$position' " +
                    "ORDER by name"
        )
    }

    suspend fun getPlayersOnTeamByPosition(position: String, team: String): List<NFLPlayer> = newSuspendedTransaction {
        executeQueryOfNflPlayers(
            "SELECT * FROM nfl " +
                    "LATEST BY name " +
                    "WHERE position = '$position' " +
                    "AND team = '$team' " +
                    "ORDER by name"
        )
    }

    suspend fun getPlayerById(playerId: Int): NFLPlayer = newSuspendedTransaction {
        executeQueryOfSinglePlayer(
            "SELECT * FROM nfl " +
                    "LATEST BY name " +
                    "WHERE id = '$playerId' " +
                    "ORDER by name"
        )
    }

    suspend fun getPlayersById(playerIds: PlayerIds): List<NFLPlayer> = newSuspendedTransaction {
        executeQueryOfNflPlayers(
            "SELECT * FROM nfl " +
                    "LATEST BY name " +
                    "WHERE id IN (${playerIds.ids.joinToString(",") { id -> "\'$id\'" }}) " +
                    "ORDER by name"
        )
    }

    suspend fun getPlayerHistory(playerId: Int): NFLPlayerStats = newSuspendedTransaction {
        executeQueryOfSinglePlayerStats(
            "SELECT * FROM nfl " +
                    "WHERE id = '$playerId' " +
                    "ORDER by timestamp DESC ")
    }

    private fun executeQueryOfNflPlayers(queryStatement: String): MutableList<NFLPlayer> {
        val nflPlayers = mutableListOf<NFLPlayer>()
        TransactionManager
            .current()
            .exec(
                queryStatement
            ) { resultSet ->
                while (resultSet.next())
                    nflPlayers.add(NFLPlayer.parse(resultSet))
            }
        return nflPlayers
    }

    private fun executeQueryOfSinglePlayer(queryStatement: String): NFLPlayer {
        var nflPlayer: NFLPlayer? = null
        TransactionManager
            .current()
            .exec(queryStatement) { resultSet ->
                while (resultSet.next())
                    nflPlayer = NFLPlayer.parse(resultSet)
            }
        return nflPlayer!!
    }

   private fun executeQueryOfSinglePlayerStats(queryStatement: String): NFLPlayerStats {
        var nflPlayerStats: NFLPlayerStats? = null
        TransactionManager
            .current()
            .exec(queryStatement) { resultSet ->
                nflPlayerStats = NFLPlayerStats.parseStatHistory(resultSet)
            }
        return nflPlayerStats!!
    }

    suspend fun getStatsFeed(): Flow<List<NFLPlayer>> =  flow{
        while (currentCoroutineContext().isActive){
            emit(getLatestPlayerStats())
            delay(10000)
        }
    }

    suspend fun getStatsFeedByPlayer(id: Int): Flow<NFLPlayer> =  flow{
        while (currentCoroutineContext().isActive){
            emit(getPlayerById(id))
            delay(10000)
        }
    }
}