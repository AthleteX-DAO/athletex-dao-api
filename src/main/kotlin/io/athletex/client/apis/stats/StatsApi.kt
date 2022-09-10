package io.athletex.client.apis.stats

import io.athletex.Sports
import io.athletex.appConfig
import io.athletex.client.Client.httpClient
import io.athletex.client.apis.stats.models.BaseballFeedUpdateItem
import io.athletex.client.apis.stats.models.BaseballPlayerInsertItem
import io.athletex.client.apis.stats.models.FootballFeedUpdateItem
import io.athletex.client.apis.stats.models.FootballPlayerInsertItem
import io.athletex.client.formulas.computeNFLPrice
import io.athletex.client.formulas.computeMLBPrice
import io.athletex.services.MLBPlayerService
import io.athletex.services.NFLPlayerService
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.config.*
import removeNonSpacingMarks
import kotlin.reflect.full.memberProperties

const val MLB_STATS_ENDPOINT = "https://api.sportsdata.io/v3/mlb/stats/json/PlayerSeasonStats/2022"
const val NFL_STATS_ENDPOINT = "https://api.sportsdata.io/v3/nfl/stats/json/PlayerSeasonStats/2022"

suspend fun syncStatsToDb(sports: Sports) {
    when (sports) {
        Sports.MLB -> syncMLBStatsToDb(MLBPlayerService(), appConfig)
        Sports.NFL -> syncNFLStatsToDb(NFLPlayerService(), appConfig)
    }
}

suspend fun syncNFLStatsToDb(nflPlayerService: NFLPlayerService, config: HoconApplicationConfig = appConfig) {
    val nflApiKey = config.property("api.nflApiKey").getString()
    val response = httpClient.request(NFL_STATS_ENDPOINT) {
        method = HttpMethod.Get
        headers.append("Ocp-Apim-Subscription-Key", nflApiKey)
    }
    val playerStatsResponse = response.body<List<FootballFeedUpdateItem>>()
    val statsUpdate = parseNFLStatsUpdateResponse(playerStatsResponse)
    nflPlayerService.insertPlayers(statsUpdate)
}

private fun parseNFLStatsUpdateResponse(playerStatsResponse: List<FootballFeedUpdateItem>): List<FootballPlayerInsertItem> {
    println("NFL stats response size = ${playerStatsResponse.size}")
    val statsUpdate: MutableList<FootballPlayerInsertItem> = playerStatsResponse.map { playerUpdate ->
        val name = playerUpdate.name.removeNonSpacingMarks()
        val computedPrice = computeNFLPrice(playerUpdate)
        FootballPlayerInsertItem(
            name = name,
            id = playerUpdate.playerId,
            team = playerUpdate.team,
            position = playerUpdate.position,
            passingYards = playerUpdate.passingYards,
            passingTouchdowns = playerUpdate.passingTouchdowns,
            reception = playerUpdate.receptions,
            receivingYards = playerUpdate.receivingYards,
            receivingTouchdowns = playerUpdate.receivingTouchdowns,
            rushingYards = playerUpdate.rushingYards,
            rushingTouchdowns = playerUpdate.rushingTouchdowns,
            passingInterceptions = playerUpdate.passingInterceptions,
            offensiveSnapsPlayed = playerUpdate.offensiveSnapsPlayed,
            defensiveSnapsPlayed = playerUpdate.defensiveSnapsPlayed,
            fumblesLost = playerUpdate.fumblesLost,
            price = computedPrice
        )
    }.toMutableList()

    getDefaultNflPlayers().forEach { defaultPlayer ->
        val existingPlayer = statsUpdate.find { it.id == defaultPlayer.id }
        if (existingPlayer == null) {
            println("adding missing player ${defaultPlayer.name}")
            statsUpdate.add(defaultPlayer)
        }
    }
    return statsUpdate
}

fun getDefaultNflPlayers(): List<FootballPlayerInsertItem> {
    return listOf(
        FootballPlayerInsertItem(
            name = "J.Allen",
            id = 19801,
            team = "BUF",
            position = "QB",
        ),
        FootballPlayerInsertItem(
            name = "J.Herbert",
            id = 21681,
            team = "LAC",
            position = "QB",
        ),
        FootballPlayerInsertItem(
            name = "P.Mahomes",
            id = 18890,
            team = "KC",
            position = "QB",
        ),
        FootballPlayerInsertItem(
            name = "L.Jackson",
            id = 22049,
            team = "BAL",
            position = "QB",
        ),
        FootballPlayerInsertItem(
            name = "J.Taylor",
            id = 21682,
            team = "IND",
            position = "RB",
        ),
        FootballPlayerInsertItem(
            name = "D.Henry",
            id = 17959,
            team = "TEN",
            position = "RB",
        ),
        FootballPlayerInsertItem(
            name = "C.McCaffrey",
            id = 18877,
            team = "CAR",
            position = "RB",
        ),
        FootballPlayerInsertItem(
            name = "A.Ekeler",
            id = 19562,
            team = "LAC",
            position = "RB",
        ),
        FootballPlayerInsertItem(
            name = "C.Kupp",
            id = 18882,
            team = "LAR",
            position = "WR",
        ),
        FootballPlayerInsertItem(
            name = "J.Jefferson",
            id = 21685,
            team = "MIN",
            position = "WR",
        ),
        FootballPlayerInsertItem(
            name = "J.Chase",
            id = 22564,
            team = "CIN",
            position = "WR",
        ),
        FootballPlayerInsertItem(
            name = "T.Kelce",
            id = 15048,
            team = "KC",
            position = "TE",
        ),
        FootballPlayerInsertItem(
            name = "M.Andrews",
            id = 19803,
            team = "BAL",
            position = "TE",
        ),
        FootballPlayerInsertItem(
            name = "K.Pitts",
            id = 22508,
            team = "ATL",
            position = "TE",
        ),
        FootballPlayerInsertItem(
            name = "G.Kittle",
            id = 19063,
            team = "SF",
            position = "TE",
        ),
        FootballPlayerInsertItem(
            name = "J.Fields",
            id = 22492,
            team = "CHI",
            position = "QB",
        ),
    )

}

suspend fun syncMLBStatsToDb(mlbPlayerService: MLBPlayerService, config: HoconApplicationConfig = appConfig) {
    val mlbApiKey = config.property("api.mlbApiKey").getString()
    val response = httpClient.request(MLB_STATS_ENDPOINT) {
        method = HttpMethod.Get
        headers.append("Ocp-Apim-Subscription-Key", mlbApiKey)
    }
    val playerStatsResponse = response.body<List<BaseballFeedUpdateItem>>()
    val statsUpdate = parseMLBStatsUpdateResponse(playerStatsResponse)
    mlbPlayerService.insertPlayers(statsUpdate)
}

private fun parseMLBStatsUpdateResponse(playerStatsResponse: List<BaseballFeedUpdateItem>): List<BaseballPlayerInsertItem> {
    println("MLB stats response size = ${playerStatsResponse.size}")

    var lgWeightOnBase = 0.0
    var sumLeaguePlateAppearances = 0.0
    playerStatsResponse.filter { (it.plateAppearances ?: 0.0) > 0 }.forEach {
        lgWeightOnBase += it.weightedOnBasePercentage ?: 0.0
        sumLeaguePlateAppearances += it.plateAppearances ?: 0.0
    }
    lgWeightOnBase /= playerStatsResponse.size

    val statsUpdate = playerStatsResponse.map { playerUpdate ->
        val name = playerUpdate.name.removeNonSpacingMarks()
        val inningsPlayed = (playerUpdate.games ?: 0.0) * 9.0
        val computedPrice = computeMLBPrice(playerUpdate, lgWeightOnBase, sumLeaguePlateAppearances)
        BaseballPlayerInsertItem(
            id = playerUpdate.playerID,
            name = name,
            team = playerUpdate.team,
            position = playerUpdate.position,
            started = playerUpdate.started,
            games = playerUpdate.games,
            atBats = playerUpdate.atBats,
            runs = playerUpdate.runs,
            singles = playerUpdate.singles,
            doubles = playerUpdate.doubles,
            triples = playerUpdate.triples,
            homeRuns = playerUpdate.homeRuns,
            inningsPlayed = inningsPlayed,
            battingAverage = playerUpdate.battingAverage,
            outs = playerUpdate.outs,
            walks = playerUpdate.walks,
            errors = playerUpdate.errors,
            saves = playerUpdate.saves,
            strikeOuts = playerUpdate.strikeouts,
            stolenBases = playerUpdate.stolenBases,
            plateAppearances = playerUpdate.plateAppearances,
            weightedOnBasePercentage = playerUpdate.weightedOnBasePercentage,
            price = computedPrice
        )
    }
    statsUpdate::class.memberProperties.forEach {
        println("${it::name} = $it")
    }
    return statsUpdate
}
