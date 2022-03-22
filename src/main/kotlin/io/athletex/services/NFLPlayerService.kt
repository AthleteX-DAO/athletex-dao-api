package io.athletex.services

import io.athletex.db.Table.NFL
import io.athletex.db.queryLatestPlayerRecordsNameOrdered
import io.athletex.model.nfl.NFLPlayer
import io.athletex.model.nfl.NFLPlayerStats
import io.athletex.routes.payloads.PlayerIds
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class NFLPlayerService : PlayerService{

    override suspend fun getAllPlayers(): List<NFLPlayer> = newSuspendedTransaction {
        executeQueryOfNflPlayers(
            queryLatestPlayerRecordsNameOrdered(NFL)
        )
    }

    private suspend fun getLatestPlayerStats(): List<NFLPlayer> = newSuspendedTransaction {
        executeQueryOfNflPlayers(
            "SELECT * FROM nfl " +
                    "LATEST BY name " +
                    "ORDER by timestamp DESC "
        )
    }

    override suspend fun getPlayersByTeam(team: String): List<NFLPlayer> = newSuspendedTransaction {
        executeQueryOfNflPlayers(
            "SELECT * FROM nfl " +
                    "LATEST BY name " +
                    "WHERE team = '$team' " +
                    "ORDER by name"
        )
    }

    override suspend fun getPlayersByPosition(position: String): List<NFLPlayer> = newSuspendedTransaction {
        executeQueryOfNflPlayers(
            "SELECT * FROM nfl " +
                    "LATEST BY name " +
                    "WHERE position = '$position' " +
                    "ORDER by name"
        )
    }



    override suspend fun getPlayersOnTeamByPosition(position: String, team: String): List<NFLPlayer> = newSuspendedTransaction {
        executeQueryOfNflPlayers(
            "SELECT * FROM nfl " +
                    "LATEST BY name " +
                    "WHERE position = '$position' " +
                    "AND team = '$team' " +
                    "ORDER by name"
        )
    }

    suspend fun getPlayerById(playerId: Int, targetDate: String? = null): NFLPlayer = newSuspendedTransaction {
        executeQueryOfSinglePlayer(
            "SELECT * FROM nfl " +
                    "LATEST BY name " +
                    "WHERE id = '$playerId' " +
                    if(targetDate != null) addTimestampBound(targetDate) else "" +
                    "ORDER by name"
        )
    }

    override suspend fun getPlayersById(playerIds: PlayerIds): List<NFLPlayer> = newSuspendedTransaction {
        executeQueryOfNflPlayers(
            "SELECT * FROM nfl " +
                    "LATEST BY name " +
                    "WHERE id IN (${playerIds.ids.joinToString(",") { id -> "\'$id\'" }}) " +
                    "ORDER by name"
        )
    }

    suspend fun getPlayerHistory(playerId: Int, from: String?, until: String?): NFLPlayerStats = newSuspendedTransaction {
        executeQueryOfSinglePlayerStats(
            "SELECT * FROM nfl " +
                    "WHERE id = '$playerId' " +
                    addTimeFilter(from, until) +
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
                    nflPlayers.add(NFLPlayer.parseSQL(resultSet))
            }
        return nflPlayers
    }

    private fun executeQueryOfSinglePlayer(queryStatement: String): NFLPlayer {
        var nflPlayer: NFLPlayer? = null
        TransactionManager
            .current()
            .exec(queryStatement) { resultSet ->
                while (resultSet.next())
                    nflPlayer = NFLPlayer.parseSQL(resultSet)
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

    private fun addTimestampBound(targetDate: String): String =
        "AND '$targetDate' = to_str(timestamp, 'yyyy-MM-dd')"

    private fun addTimeFilter(fromDate: String?, untilDate: String?): String =
        when{
            (!fromDate.isNullOrEmpty() && !untilDate.isNullOrEmpty()) -> addTimestampRange(fromDate, untilDate)
            (!fromDate.isNullOrEmpty()) -> addFromTimestamp(fromDate)
            (!untilDate.isNullOrEmpty()) -> addUntilTimestamp(untilDate)
            else -> ""
        }

    private fun addFromTimestamp(fromDate: String): String =
        "timestamp > to_timestamp('$fromDate:00:00:00.000000Z', 'yyyy-MM-dd:HH:mm:ss.SSSUUUZ')"

    private fun addUntilTimestamp(untilDate: String): String =
        "timestamp < to_timestamp('$untilDate:00:00:00.000000Z', 'yyyy-MM-dd:HH:mm:ss.SSSUUUZ')"

    private fun addTimestampRange(fromDate: String, untilDate: String): String =
        "AND timestamp " +
        "BETWEEN to_timestamp('$fromDate:00:00:00.000000Z', 'yyyy-MM-dd:HH:mm:ss.SSSUUUZ') " +
        "AND to_timestamp('$untilDate:00:00:00.000000Z', 'yyyy-MM-dd:HH:mm:ss.SSSUUUZ')"
}