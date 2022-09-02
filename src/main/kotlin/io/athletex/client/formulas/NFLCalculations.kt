package io.athletex.client.formulas

import io.athletex.client.apis.stats.models.FootballFeedUpdateItem

fun computeNFLPrice(feedUpdateItem: FootballFeedUpdateItem): Double {
    
    // Variables
    val passingYards = feedUpdateItem.passingYards / 25
    val rushingYards = feedUpdateItem.rushingYards / 10
    val receivingYards = feedUpdateItem.receivingYards / 10
    val rushingTouchdowns = feedUpdateItem.rushingTouchdowns * 6
    val receivingTouchdowns = feedUpdateItem.receivingTouchdowns * 6
    val passTD = feedUpdateItem.passingTouchdowns * 4
    val reception = feedUpdateItem.receptions * 0.5
    val passingIntercept = feedUpdateItem.passingInterceptions * -2
    val fumblesLost = feedUpdateItem.fumblesLost * -2

    // Football Athletes
    var numerator = passingYards + rushingYards + receivingYards + rushingTouchdowns + receivingTouchdowns + passTD + reception + passingIntercept + fumblesLost
    var denominator = feedUpdateItem.offensiveSnapsPlayed
    if (denominator == 0.0) {
        denominator = feedUpdateItem.defensiveSnapsPlayed
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
