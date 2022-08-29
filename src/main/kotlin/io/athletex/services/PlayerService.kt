package io.athletex.services

import io.athletex.db.Table
import io.athletex.db.queryPriceHistoryForSinglePlayer
import io.athletex.model.Player
import io.athletex.model.PlayerPriceHistory
import io.athletex.model.PlayerPriceHistory.Companion.parsePriceHistory
import io.athletex.model.PlayerStats
import io.athletex.routes.payloads.PlayerIds
import kotlinx.coroutines.flow.Flow
import org.jetbrains.exposed.sql.statements.StatementType
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.sql.ResultSet

interface PlayerService {
    val table: Table
    suspend fun getAllPlayers(): List<Player>
    suspend fun getPlayersByTeam(team: String): List<Player>
    suspend fun getPlayersByPosition(position: String): List<Player>
    suspend fun getPlayersOnTeamByPosition(position: String, team: String): List<Player>
    suspend fun getPlayersById(playerIds: PlayerIds): List<Player>
    suspend fun getPlayerById(playerId: Int, targetDate: String? = null): Player
    suspend fun getLatestPlayerStats(): List<Player>
    suspend fun getPlayerHistory(playerId: Int, from: String?, until: String?): PlayerStats
    suspend fun getPlayersHistories(playerIds: PlayerIds, from: String?, until: String?): List<PlayerStats>
    suspend fun getStatsFeed(): Flow<List<Player>>
    suspend fun getStatsFeedByPlayer(id: Int): Flow<Player>
    suspend fun updateDatabase();

    suspend fun getPlayerPriceHistory(
        playerId: Int,
        from: String?,
        until: String?,
        interval: String
    ): PlayerPriceHistory =
        newSuspendedTransaction {
            executeQueryOfSinglePlayerPriceHistory(
                queryPriceHistoryForSinglePlayer(
                    playerId,
                    from,
                    until,
                    table,
                    interval
                )
            )
        }

    fun <T : Player> executeQueryOfPlayers(queryStatement: String, parseSql: (ResultSet) -> T): MutableList<T> {
        val players = mutableListOf<T>()
        TransactionManager
            .current()
            .exec(
                queryStatement
            ) { resultSet ->
                while (resultSet.next())
                    players.add(parseSql(resultSet))
            }
        return players
    }

    fun <T : Player> executeQueryOfSinglePlayer(queryStatement: String, parseSql: (ResultSet) -> T): T {
        var player: T? = null
        TransactionManager
            .current()
            .exec(queryStatement) { resultSet ->
                while (resultSet.next())
                    player = parseSql(resultSet)
            }
        return player!!
    }

    fun <T : PlayerStats> executeQueryOfSinglePlayerStats(
        queryStatement: String,
        parseStatHistory: (ResultSet) -> T
    ): T {
        var playerStats: T? = null
        TransactionManager
            .current()
            .exec(queryStatement) { resultSet ->
                playerStats = parseStatHistory(resultSet)
            }
        return playerStats!!
    }

    fun executeQueryOfSinglePlayerPriceHistory(queryStatement: String): PlayerPriceHistory {
        var priceRecords: PlayerPriceHistory? = null
        TransactionManager
            .current()
            .exec(queryStatement, explicitStatementType = StatementType.SELECT) { resultSet ->
                priceRecords = parsePriceHistory(resultSet)
            }
        return priceRecords!!
    }

}