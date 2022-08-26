package io.athletex.client.apis.stats

import io.athletex.Sports
import io.athletex.appConfig
import io.athletex.client.Client.httpClient
import io.athletex.client.formulas.mlb.computePrice
import io.athletex.model.mlb.MLBPlayer
import io.athletex.services.MLBPlayerService
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

}

suspend fun syncMlbStatsToDb(config: HoconApplicationConfig, mlbPlayerService: MLBPlayerService) {
    val mlbApiKey = config.property("api.mlbApiKey").getString()

    val response = httpClient.request(MLB_STATS_ENDPOINT) {
        method = HttpMethod.Get
        headers.append("Ocp-Apim-Subscription-Key", mlbApiKey)
    }
    val playerStatsResponse = response.body<List<MLBPlayer>>()
    println("MLB stats response size = ${playerStatsResponse.size}")

    var lgWeightOnBase = 0.0
    var sumLeaguePlateAppearances = 0
    playerStatsResponse.filterNot { it.plateAppearances > 0 }.forEach {
        lgWeightOnBase += it.weightedOnBasePercentage
        sumLeaguePlateAppearances += it.plateAppearances
    }
    lgWeightOnBase /= playerStatsResponse.size
    println("lgWeighOnBase=$lgWeightOnBase")
    println("sumLeaguePlateAppearances=$sumLeaguePlateAppearances")

    val statsUpdate = playerStatsResponse.map { player ->
        val name = player.name.removeNonSpacingMarks().replace(" ", "\'")
        val inningsPlayed = player.games * 9.0
        val computedPrice = computeMLBPrice(player, lgWeightOnBase, sumLeaguePlateAppearances)
        // what is this copy doing?
        player.copy(
            name = name,
            inningsPlayed = inningsPlayed,
            price = computedPrice
        )
    } //TODO filter out non supported players
    mlbPlayerService.insertStatsUpdate(statsUpdate)

    /* Thoughts (if I understand this somewhat....)
    
        statsUpdate: List<MLBPlayer>
        check player name
            - have mapping of used players
        if valid player, add a copy of them to statsUpdate
        push up statsUpdate
     */
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

    /* --------THE PYTHON CODE--------
            (athlete_data, lgweightedOnBase, sumPlateAppearances):

        [x] BattingRuns = (((athlete_data['PlateAppearances']) * (athlete_data['WeightedOnBasePercentage'] - lgweightedOnBase)) / 1.25)
        [x] BaseRunningRuns = (athlete_data['StolenBases'] * 0.2)
        [x] FieldingRuns = 0
        [x] games = (athlete_data['Games'] * 9 )
        [x] FieldingRuns = ((athlete_data['Errors'] * (-10)) / games)
        [x] if games <= 0:
        [x]         FieldingRuns = 0
        [x] PositonalAdjustment = (athlete_data['Games'] * 9 ) * position_adj.setdefault(athlete_data['Position'], 0) / 1458
        [x] ReplacementRuns = (athlete_data['PlateAppearances'] * 5561.49) / sumPlateAppearances

        # Formula Calculation
        [x] statsNumerator = BattingRuns + BaseRunningRuns + FieldingRuns + PositonalAdjustment + ReplacementRuns
        [x] WAR = statsNumerator / avg50yrRPW
        [x] computedMajorLeagueBaseballPrice = WAR # * collateralizationMultiplier
        [] return computedMajorLeagueBaseballPrice

    */
}