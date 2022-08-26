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
    var sumLeaguePlateAppearances = 0.0
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
        val computedPrice = computePrice(player, lgWeightOnBase, sumLeaguePlateAppearances)
        player.copy(
            name = name,
            inningsPlayed = inningsPlayed,
            price = computedPrice
        )
    } //TODO filter out non supported players
    mlbPlayerService.insertStatsUpdate(statsUpdate)
}
