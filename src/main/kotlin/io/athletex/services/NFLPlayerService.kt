package io.athletex.services

import io.athletex.appConfig
import io.athletex.client.apis.stats.syncNFLStatsToDb
import io.athletex.client.apis.stats.models.FootballPlayerInsertItem
import io.athletex.db.*
import io.athletex.db.Table.NFL
import io.athletex.model.PlayerStats
import io.athletex.model.nfl.NFLPlayer
import io.athletex.model.nfl.NFLPlayerStats
import io.athletex.routes.payloads.PlayerIds
import kotlinx.coroutines.currentCoroutineContext
import io.questdb.client.Sender
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class NFLPlayerService : PlayerService {
    override val table: Table = NFL

    override suspend fun getAllPlayers(): List<NFLPlayer> = newSuspendedTransaction {
        executeQueryOfPlayers(queryLatestPlayerRecords(table)) { NFLPlayer.parseSQL(it) }
    }

    override suspend fun getLatestPlayerStats(): List<NFLPlayer> = newSuspendedTransaction {
        executeQueryOfPlayers(queryStatsForSinglePlayer(table)) { NFLPlayer.parseSQL(it) }
    }

    override suspend fun getPlayersByTeam(team: String): List<NFLPlayer> = newSuspendedTransaction {
        executeQueryOfPlayers(queryLatestPlayerRecordsByTeam(team, table)) { NFLPlayer.parseSQL(it) }
    }

    override suspend fun getPlayersByPosition(position: String): List<NFLPlayer> = newSuspendedTransaction {
        executeQueryOfPlayers(queryLatestPlayerRecordsByPosition(position, table)) { NFLPlayer.parseSQL(it) }
    }

    override suspend fun getPlayersOnTeamByPosition(position: String, team: String): List<NFLPlayer> =
        newSuspendedTransaction {
            executeQueryOfPlayers(
                queryLatestPlayerRecordsOnTeamByPosition(
                    position,
                    team,
                    table
                )
            ) { NFLPlayer.parseSQL(it) }
        }

    override suspend fun getPlayerById(playerId: Int, targetDate: String?): NFLPlayer = newSuspendedTransaction {
        executeQueryOfSinglePlayer(
            queryLatestRecordForSinglePlayer(
                playerId,
                targetDate,
                table
            )
        ) { NFLPlayer.parseSQL(it) }
    }

    override suspend fun getPlayersById(playerIds: PlayerIds): List<NFLPlayer> = newSuspendedTransaction {
        executeQueryOfPlayers(queryLatestPlayerRecordsById(playerIds, table)) { NFLPlayer.parseSQL(it) }
    }

    override suspend fun getPlayerHistory(playerId: Int, from: String?, until: String?): NFLPlayerStats =
        newSuspendedTransaction {
            executeQueryOfSinglePlayerStats(
                queryRecordHistoryForSinglePlayer(
                    playerId,
                    from,
                    until,
                    table
                )
            ) { NFLPlayerStats.parseStatHistory(it) }
        }

    override suspend fun getPlayersHistories(playerIds: PlayerIds, from: String?, until: String?): List<PlayerStats> {
        return playerIds.ids.map { getPlayerHistory(it, from, until) }
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

    override suspend fun updateDatabase() {
        syncNFLStatsToDb(this)
    }

    fun insertPlayers(players: List<FootballPlayerInsertItem>) {
        val qdbHost = appConfig.property("db.url").getString()
        val sender = Sender.builder().address("$qdbHost:9009").build()
        println("Inserting ${players.size} players")
        println("Sample: ${players.first()}")
        try {
            players.forEach {
                sender.table("nfl")
                    .symbol(it::name.name, it.name)
                    .symbol(it::id.name, it.id.toString())
                    .symbol(it::team.name, it.team)
                    .symbol(it::position.name, it.position)
                    .doubleColumn(it::passingYards.name, it.passingYards ?: 0.0)
                    .doubleColumn(it::passingTouchDowns.name, it.passingTouchDowns ?: 0.0)
                    .doubleColumn(it::reception.name, it.reception ?: 0.0)
                    .doubleColumn(it::receiveYards.name, it.receiveYards ?: 0.0)
                    .doubleColumn(it::receiveTouch.name, it.receiveTouch ?: 0.0)
                    .doubleColumn(it::rushingYards.name, it.rushingYards ?: 0.0)
                    .doubleColumn(it::rushingTouch.name, it.rushingTouch ?: 0.0)
                    .doubleColumn(it::OffensiveSnapsPlayed.name, it.OffensiveSnapsPlayed ?: 0.0)
                    .doubleColumn(it::DefensiveSnapsPlayed.name, it.DefensiveSnapsPlayed ?: 0.0)
                    .doubleColumn(it::price.name, it.price ?: 0.0)
                    .atNow()
            }
        } catch (e: Exception) {
            println("Error connecting to qdb")
            e.printStackTrace()
        }
    }
}
