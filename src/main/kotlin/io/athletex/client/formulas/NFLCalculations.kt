package io.athletex.client.formulas.mlb

import io.athletex.client.apis.stats.models.BaseballFeedUpdateItem

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
