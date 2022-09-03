package io.athletex.client.formulas

import io.athletex.client.apis.stats.models.FootballFeedUpdateItem

fun computeNFLPrice(feedUpdateItem: FootballFeedUpdateItem): Double {
    
    // Variables
    val passingYards = (feedUpdateItem.passingYards ?: 0.0) / 25
    val rushingYards = (feedUpdateItem.rushingYards ?: 0.0) / 10
    val receivingYards = (feedUpdateItem.receivingYards ?: 0.0) / 10
    val rushingTouchdowns = (feedUpdateItem.rushingTouchdowns ?: 0.0) * 6
    val receivingTouchdowns = (feedUpdateItem.receivingTouchdowns ?: 0.0) * 6
    val passTD = (feedUpdateItem.passingTouchdowns ?: 0.0) * 4
    val reception = (feedUpdateItem.receptions ?: 0.0) * 0.5
    val passingIntercept = (feedUpdateItem.passingInterceptions ?: 0.0) * -2
    val fumblesLost = (feedUpdateItem.fumblesLost ?: 0.0) * -2

    // Football Athletes
    val numerator = passingYards + rushingYards + receivingYards + rushingTouchdowns + receivingTouchdowns + passTD + reception + passingIntercept + fumblesLost
    var denominator = feedUpdateItem.offensiveSnapsPlayed
    if (denominator == 0.0) {
        denominator = feedUpdateItem.defensiveSnapsPlayed
    }
    if (denominator == 0.0 || denominator == null) {
        denominator = 1.0
    }

    var computedAmericanFootballPrice = numerator / (denominator)
    if (computedAmericanFootballPrice < 0.0) {
            computedAmericanFootballPrice = 0.0
    }

    return computedAmericanFootballPrice
}
