package io.athletex.services

import io.athletex.db.*
import io.athletex.db.Table.NFL
import io.athletex.model.nfl.NFLPlayer
import io.athletex.model.nfl.NFLPlayerStats
import io.athletex.routes.payloads.PlayerIds
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class NFLPlayerService : PlayerService {

    override suspend fun getAllPlayers(): List<NFLPlayer> = newSuspendedTransaction {
        executeQueryOfPlayers(queryLatestPlayerRecords(NFL)) { NFLPlayer.parseSQL(it) }
    }

    override suspend fun getLatestPlayerStats(): List<NFLPlayer> = newSuspendedTransaction {
        executeQueryOfPlayers(queryStatsForSinglePlayer(NFL)) { NFLPlayer.parseSQL(it) }
    }

    override suspend fun getPlayersByTeam(team: String): List<NFLPlayer> = newSuspendedTransaction {
        executeQueryOfPlayers(queryLatestPlayerRecordsByTeam(team, NFL)) { NFLPlayer.parseSQL(it) }
    }

    override suspend fun getPlayersByPosition(position: String): List<NFLPlayer> = newSuspendedTransaction {
        executeQueryOfPlayers(queryLatestPlayerRecordsByPosition(position, NFL)) { NFLPlayer.parseSQL(it) }
    }

    override suspend fun getPlayersOnTeamByPosition(position: String, team: String): List<NFLPlayer> =
        newSuspendedTransaction {
            executeQueryOfPlayers(
                queryLatestPlayerRecordsOnTeamByPosition(
                    position,
                    team,
                    NFL
                )
            ) { NFLPlayer.parseSQL(it) }
        }

    override suspend fun getPlayerById(playerId: Int, targetDate: String?): NFLPlayer = newSuspendedTransaction {
        executeQueryOfSinglePlayer(
            queryLatestRecordForSinglePlayer(
                playerId,
                targetDate,
                NFL
            )
        ) { NFLPlayer.parseSQL(it) }
    }

    override suspend fun getPlayersById(playerIds: PlayerIds): List<NFLPlayer> = newSuspendedTransaction {
        executeQueryOfPlayers(queryLatestPlayerRecordsById(playerIds, NFL)) { NFLPlayer.parseSQL(it) }
    }

    override suspend fun getPlayerHistory(playerId: Int, from: String?, until: String?): NFLPlayerStats =
        newSuspendedTransaction {
            executeQueryOfSinglePlayerStats(
                queryRecordHistoryForSinglePlayer(
                    playerId,
                    from,
                    until,
                    NFL
                )
            ) { NFLPlayerStats.parseStatHistory(it) }
        }

    override suspend fun getStatsFeed(): Flow<List<NFLPlayer>> = flow {
        while (currentCoroutineContext().isActive) {
            emit(getLatestPlayerStats())
            delay(10000)
        }
    }

    override suspend fun getStatsFeedByPlayer(id: Int): Flow<NFLPlayer> = flow {
        while (currentCoroutineContext().isActive) {
            emit(getPlayerById(id))
            delay(10000)
        }
    }
}