package io.athletex.services

import io.athletex.db.*
import io.athletex.db.Table.MLB
import io.athletex.model.mlb.MLBPlayer
import io.athletex.model.mlb.MLBPlayerStats
import io.athletex.routes.payloads.PlayerIds
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class MLBPlayerService : PlayerService {
    override val table: Table = MLB

    override suspend fun getAllPlayers(): List<MLBPlayer> = newSuspendedTransaction {
        executeQueryOfPlayers(queryLatestPlayerRecords(table)) { MLBPlayer.parseSQL(it) }
    }

    override suspend fun getPlayersByTeam(team: String): List<MLBPlayer> = newSuspendedTransaction {
        executeQueryOfPlayers(queryLatestPlayerRecordsByTeam(team, table)) { MLBPlayer.parseSQL(it) }
    }

    override suspend fun getPlayersByPosition(position: String): List<MLBPlayer> = newSuspendedTransaction {
        executeQueryOfPlayers(queryLatestPlayerRecordsByPosition(position, table)) { MLBPlayer.parseSQL(it) }
    }

    override suspend fun getPlayersOnTeamByPosition(position: String, team: String): List<MLBPlayer> =
        newSuspendedTransaction {
            executeQueryOfPlayers(
                queryLatestPlayerRecordsOnTeamByPosition(
                    position,
                    team,
                    table
                )
            ) { MLBPlayer.parseSQL(it) }
        }

    override suspend fun getPlayersById(playerIds: PlayerIds): List<MLBPlayer> = newSuspendedTransaction {
        executeQueryOfPlayers(queryLatestPlayerRecordsById(playerIds, table)) { MLBPlayer.parseSQL(it) }
    }

    override suspend fun getPlayerById(playerId: Int, targetDate: String?): MLBPlayer = newSuspendedTransaction {
        executeQueryOfSinglePlayer(
            queryLatestRecordForSinglePlayer(
                playerId,
                targetDate,
                table
            )
        ) { MLBPlayer.parseSQL(it) }
    }

    override suspend fun getPlayerHistory(playerId: Int, from: String?, until: String?): MLBPlayerStats =
        newSuspendedTransaction {
            executeQueryOfSinglePlayerStats(
                queryRecordHistoryForSinglePlayer(
                    playerId,
                    from,
                    until,
                    table
                )
            ) { MLBPlayerStats.parseStatHistory(it) }
        }

    override suspend fun getPlayersHistories(playerIds: PlayerIds, from: String?, until: String?): List<MLBPlayerStats> {
        return playerIds.ids.map { getPlayerHistory(it, from, until) }
    }

    override suspend fun getLatestPlayerStats(): List<MLBPlayer> = newSuspendedTransaction {
        executeQueryOfPlayers(queryStatsForSinglePlayer(table)) { MLBPlayer.parseSQL(it) }
    }

    override suspend fun getStatsFeed(): Flow<List<MLBPlayer>> = flow {
        while (currentCoroutineContext().isActive) {
            emit(getLatestPlayerStats())
            delay(10000)
        }
    }

    override suspend fun getStatsFeedByPlayer(id: Int): Flow<MLBPlayer> = flow {
        while (currentCoroutineContext().isActive) {
            emit(getPlayerById(id))
            delay(10000)
        }
    }
}
