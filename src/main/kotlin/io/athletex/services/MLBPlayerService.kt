package io.athletex.services

import io.athletex.db.Table.MLB
import io.athletex.db.queryLatestPlayerRecordsNameOrdered
import io.athletex.model.mlb.MLBPlayer
import io.athletex.routes.payloads.PlayerIds
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class MLBPlayerService: PlayerService {

     override suspend fun getAllPlayers(): List<MLBPlayer> = newSuspendedTransaction {
        executeQueryOfMlbPlayers(
            queryLatestPlayerRecordsNameOrdered(MLB)
        )
    }

    private fun executeQueryOfMlbPlayers(queryStatement: String): MutableList<MLBPlayer> {
        val mlbPlayers = mutableListOf<MLBPlayer>()
        TransactionManager
            .current()
            .exec(
                queryStatement
            ) { resultSet ->
                while (resultSet.next())
                    mlbPlayers.add(MLBPlayer.parseSQL(resultSet))
            }
        return mlbPlayers
    }

    override suspend fun getPlayersByTeam(team: String): List<Any> {
        TODO("Not yet implemented")
    }

    override suspend fun getPlayersByPosition(position: String): List<Any> {
        TODO("Not yet implemented")
    }

    override suspend fun getPlayersOnTeamByPosition(position: String, team: String): List<Any> {
        TODO("Not yet implemented")
    }

    override suspend fun getPlayersById(playerIds: PlayerIds): List<Any> {
        TODO("Not yet implemented")
    }
}