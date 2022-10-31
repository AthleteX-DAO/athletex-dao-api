package io.athletex.services

import io.athletex.appConfig
import io.athletex.client.apis.stats.syncNBAStatsToDb
import io.athletex.client.apis.stats.models.BasketballPlayerInsertItem
import io.athletex.db.*
import io.athletex.db.Table.NBA
import io.athletex.model.PlayerStats
import io.athletex.model.nba.NBAPlayer
import io.athletex.model.nba.NBAPlayerStats
import io.athletex.routes.payloads.PlayerIds
import kotlinx.coroutines.currentCoroutineContext
import io.questdb.client.Sender
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class NBAPlayerService : PlayerService {
    override val table: Table = NBA

    override suspend fun getAllPlayers(): List<NBAPlayer> = newSuspendedTransaction {
        executeQueryOfPlayers(queryLatestPlayerRecords(table)) { NBAPlayer.parseSQL(it) }
    }

    override suspend fun getLatestPlayerStats(): List<NBAPlayer> = newSuspendedTransaction {
        executeQueryOfPlayers(queryStatsForSinglePlayer(table)) { NBAPlayer.parseSQL(it) }
    }

    override suspend fun getPlayersByTeam(team: String): List<NBAPlayer> = newSuspendedTransaction {
        executeQueryOfPlayers(queryLatestPlayerRecordsByTeam(team, table)) { NBAPlayer.parseSQL(it) }
    }

    override suspend fun getPlayersByPosition(position: String): List<NBAPlayer> = newSuspendedTransaction {
        executeQueryOfPlayers(queryLatestPlayerRecordsByPosition(position, table)) { NBAPlayer.parseSQL(it) }
    }

    override suspend fun getPlayersOnTeamByPosition(position: String, team: String): List<NBAPlayer> =
    newSuspendedTransaction {
        executeQueryOfPlayers(
            queryLatestPlayerRecordsOnTeamByPosition(
                position,
                team,
                table
            )
        ) { NBAPlayer.parseSQL(it) }
    }

    override suspend fun getPlayerById(playerId: Int, targetDate: String?): NBAPlayer = newSuspendedTransaction {
        executeQueryOfSinglePlayer(
            queryLatestRecordForSinglePlayer(
                playerId,
                targetDate,
                table
            )
        ) { NBAPlayer.parseSQL(it) }
    }

    override suspend fun getPlayersById(playerIds: PlayerIds): List<NBAPlayer> = newSuspendedTransaction {
        executeQueryOfPlayers(queryLatestPlayerRecordsById(playerIds, table)) { NBAPlayer.parseSQL(it) }
    }

    override suspend fun getPlayerHistory(playerId: Int, from: String?, until: String?): NBAPlayerStats =
    newSuspendedTransaction {
        executeQueryOfSinglePlayerStats(
            queryRecordHistoryForSinglePlayer(
                playerId,
                from,
                until,
                table
            )
        ) { NBAPlayerStats.parseStatHistory(it) }
    }

    override suspend fun getPlayersHistories(playerIds: PlayerIds, from: String?, until: String?): List<PlayerStats> {
        return playerIds.ids.map { getPlayerHistory(it, from, until) }
    }

    override suspend fun getStatsFeed(): Flow<List<NBAPlayer>> = flow {
        while (currentCoroutineContext().isActive) {
            emit(getLatestPlayerStats())
            delay(10000)
        }
    }

    override suspend fun getStatsFeedByPlayer(id: Int): Flow<NBAPlayer> = flow {
        while (currentCoroutineContext().isActive) {
            emit(getPlayerById(id))
            delay(10000)
        }
    }

    override suspend fun updateDatabase() {
        syncNBAStatsToDb(this)
    }

    fun insertPlayers(players: List<BasketballPlayerInsertItem>) {
        val qdbHost = appConfig.property("db.url").getString()
        val sender = Sender.builder().address("$qdbHost:9009").build()
        println("Inserting ${players.size} players")
        println("Sample: ${players.first()}")
        try {
            players.forEach {
                sender.table("nba")
                    .symbol(it::name.name, it.name)
                    .symbol(it::id.name, it.id.toString())
                    .doubleColumn(it::price.name, it.price ?: 0.0)
                    .atNow()
            }
        } catch (e: Exception) {
            println("Error connecting to qdb")
            e.printStackTrace()
        }
        sender.flush()
    }

    override fun executeDropTable(queryStatement: String) {
        super.executeDropTable(queryStatement)
    }
}
