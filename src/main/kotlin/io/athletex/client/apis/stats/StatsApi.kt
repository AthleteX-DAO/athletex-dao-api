package io.athletex.client.apis.stats

import io.athletex.Sports
import io.athletex.appConfig
import io.athletex.client.Client.httpClient
import io.athletex.client.formulas.mlb.computePrice
import io.athletex.model.mlb.MLBPlayer
import io.athletex.services.MLBPlayerService
import io.athletex.model.nfl.NFLPlayer
import io.athletex.services.NFLPlayerService
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.config.*
import removeNonSpacingMarks

const val MLB_STATS_ENDPOINT = "https://api.sportsdata.io/v3/mlb/stats/json/PlayerSeasonStats/2022"
const val NFL_STATS_ENDPOINT = "https://api.sportsdata.io/v3/mlb/stats/json/PlayerSeasonStats/2022"

suspend fun syncStatsToDb(sports: Sports) {
    when (sports) {
        Sports.MLB -> syncMlbStatsToDb(appConfig, MLBPlayerService())
        Sports.NFL -> syncNflStatsToDb()
    }
}

suspend fun syncNflStatsToDb() {
    val nflApiKey = config.property("api.nflApiKey").getString()

    val response = httpClient.request(NFL_STATS_ENDPOINT) {
        method = HttpMethod.Get
        headers.append("Ocp-Apim-Subscription-Key", nflApiKey)
    }
    val playerStatsResponse = response.body<List<NFLPlayer>>()
    println("NFL stats response size = ${playerStatsResponse.size}")

    val statsUpdate = playerStatsResponse.map { player ->
        val name = player.name.removeNonSpacingMarks().replace(" ", "\'")
        val computedPrice = computeNFLPrice(player)
        
        player.copy(
            name = name,
            price = computedPrice
        )
    } //TODO filter out non supported players
    nflPlayerService.insertStatsUpdate(statsUpdate)
}

suspend fun syncMlbStatsToDb(mlbPlayerService: MLBPlayerService, config: HoconApplicationConfig = appConfig) {
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
    playerStatsResponse.filter { it.plateAppearances > 0 }.forEach {
        lgWeightOnBase += it.weightedOnBasePercentage
        sumLeaguePlateAppearances += it.plateAppearances
    }
    lgWeightOnBase /= playerStatsResponse.size

    val statsUpdate = playerStatsResponse.map { playerUpdate ->
        val name = playerUpdate.name.removeNonSpacingMarks()
        val inningsPlayed = playerUpdate.games * 9.0
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
            weightedOnBasePercentage = playerUpdate.weightedOnBasePercentage.toDouble(),
            price = computedPrice
        )
    }
    statsUpdate::class.memberProperties.forEach {
        println("${it::name} = $it")
    }
    return statsUpdate
}  

fun computeMLBPrice(athlete: MLBPlayer, lgWeightOnBase: Double, sumPlateAppearances: Int): Double {
    // map position weights
    val position_adj = mapOf(
        "C" to 12.5,
        "1B" to -12.5,
        "2B" to 2.5,
        "SS" to 7.5,
        "3B" to 2.5,
        "LF" to -7.5,
        "CF" to 2.5,
        "RF" to -7.5,
        "DH" to -17.5
    )
    var avg50yrRPW = 9.757

    // prepping calculation
    var battingRuns = (athlete.plateAppearances * (athlete.weightedOnBasePercentage - lgWeightOnBase)) / 1.25
    var baseRunningRuns = athlete.stolenBases * 0.2
    var games = athlete.games * 9
    var fieldingRuns
    if (games <= 0) {
        fieldingRuns = athlete.errors * -10 / games
    } else {
        fieldingRuns = 0
    }
    var positionAdjustment = ( athlete.games * 9 ) * position_adj[athlete.position] / 1458
    var replacementRuns = ( athlete.plateAppearances * 5561.49 ) / sumPlateAppearances

    // formula calculation
    var statsNumerator = battingRuns + baseRunningRuns + fieldingRuns + positionAdjustment + replacementRuns
    var war = statsNumerator / avg50yrRPW
    var computedMajorLeagueBaseballPrice = war // * collateralizationMultiplier

    return computedMajorLeagueBaseballPrice
}

fun computeNFLPrice(athlete: NFLPlayer): Double {
    
    // Variables
    val passingYards = athlete.passingYards / 25
    val rushingYards = athlete.rushingYards / 10
    val receivingYards = athlete.receivingYards / 10
    val rushingTouchdowns = athlete.rushingTouchdowns * 6
    val receivingTouchdowns = athlete.receivingTouchdowns * 6
    val passTD = athlete.passingTouchdowns * 4
    val reception = athlete.receptions * 0.5
    val passingIntercept = athlete.passingInterceptions * -2
    val fumblesLost = athlete.fumblesLost * -2

    // Football Athletes
    var numerator = passingYards + rushingYards + receivingYards + rushingTouchdowns + receivingTouchdowns + passTD + reception + passingIntercept + fumblesLost
    var denominator = athlete.offensiveSnapsPlayed
    if (denominator == 0.0) {
        denominator = athlete.defensiveSnapsPlayed
    }
    if (denominator == 0.0) {
        denominator = 1.0
    }

    var computedAmericanFootballPrice = numerator / denominator
    if (computedAmericanFootballPrice < 0.0) {
            computedAmericanFootballPrice = 0.0
    }

    return computedAmericanFootballPrice
}

