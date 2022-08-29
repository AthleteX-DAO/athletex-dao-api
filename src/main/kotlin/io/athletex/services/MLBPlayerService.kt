package io.athletex.services

import io.athletex.appConfig
import io.athletex.client.apis.stats.models.BaseballPlayerInsertItem
import io.athletex.client.apis.stats.syncMlbStatsToDb
import io.athletex.db.*
import io.athletex.db.Table.MLB
import io.athletex.model.mlb.MLBPlayer
import io.athletex.model.mlb.MLBPlayerStats
import io.athletex.routes.payloads.PlayerIds
import io.questdb.client.Sender
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
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

    override suspend fun updateDatabase() {
        syncMlbStatsToDb(this)
    }

    fun insertPlayers(players: List<BaseballPlayerInsertItem>) {
        val qdbHost = appConfig.property("db.url").getString()
        val sender = Sender.builder().address("$qdbHost:9009").build()
        println("Inserting ${players.size} players")
        println("Sample: ${players.first()}")
        players.forEach {
            sender.table("mlb")
                .symbol(it::name.name, it.name)
                .symbol(it::id.name, it.id.toString())
                .symbol(it::team.name, it.team)
                .symbol(it::position.name, it.position)
                .doubleColumn(it::started.name, it.started)
                .doubleColumn(it::games.name, it.games)
                .doubleColumn(it::atBats.name, it.atBats)
                .doubleColumn(it::runs.name, it.runs)
                .doubleColumn(it::singles.name, it.singles)
                .doubleColumn(it::doubles.name, it.doubles)
                .doubleColumn(it::triples.name, it.triples)
                .doubleColumn(it::homeRuns.name, it.homeRuns)
                .doubleColumn(it::inningsPlayed.name, it.inningsPlayed)
                .doubleColumn(it::battingAverage.name, it.battingAverage)
                .doubleColumn(it::outs.name, it.outs)
                .doubleColumn(it::walks.name, it.walks)
                .doubleColumn(it::errors.name, it.errors)
                .doubleColumn(it::plateAppearances.name, it.plateAppearances)
                .doubleColumn(it::weightedOnBasePercentage.name, it.weightedOnBasePercentage)
                .doubleColumn(it::saves.name, it.saves)
                .doubleColumn(it::strikeOuts.name, it.strikeOuts)
                .doubleColumn(it::stolenBases.name, it.stolenBases)
                .doubleColumn(it::price.name, it.price)
                .atNow()
        }
        try {
        } catch (e: Exception) {
            println("Error connecting to qdb")
            e.printStackTrace()
        }
    }
}
