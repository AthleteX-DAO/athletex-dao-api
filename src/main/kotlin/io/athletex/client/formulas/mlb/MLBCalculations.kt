package io.athletex.client.formulas.mlb

import io.athletex.client.apis.stats.models.BaseballFeedUpdateItem


val mlbPositionalAdjustments = mapOf(
    "C" to 12.5,
    "1B" to -12.5,
    "2B" to 2.5,
    "3B" to 2.5,
    "SS" to 7.5,
    "LF" to -7.5,
    "CF" to 2.5,
    "RF" to -7.5,
    "DH" to -17.5,
)

fun computePrice(
    feedUpdateItem: BaseballFeedUpdateItem,
    lgWeightOnBase: Double,
    sumLeaguePlateAppearances: Double
): Double {
    val avg50yrRPW = 9.757
    // Note -- The collateralization multiplier is meant to scale the calculated WAR price, this may be needed in the future, or further down the stack
    // collateralizationMultiplier = 1000
    val battingRuns =
        (((((feedUpdateItem.plateAppearances) * (feedUpdateItem.weightedOnBasePercentage - lgWeightOnBase)) / 1.25)))
    val baseRunningRuns = (feedUpdateItem.stolenBases * 0.2)
    val fieldingRuns =
        (((feedUpdateItem.errors * -10)) / (feedUpdateItem.games * 9)).let { if (it.isNaN()) 0.0 else it }
    val positionalAdjustments =
        (((feedUpdateItem.games * 9) * (mlbPositionalAdjustments[feedUpdateItem.position] ?: 0.0))) / 1458.0
    val replacementRuns =
        ((feedUpdateItem.plateAppearances * 5561.49) / sumLeaguePlateAppearances).let { if (it.isNaN()) 0.0 else it }

    val statsNumerator = battingRuns + baseRunningRuns + fieldingRuns + positionalAdjustments + replacementRuns

    // if WAR is less than 0, then set it to 0
    return (statsNumerator / avg50yrRPW).let { if (it < 0.0) 0.0 else it }  //WAR price
}
