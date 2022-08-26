package io.athletex.client.formulas.mlb

import io.athletex.model.mlb.MLBPlayer


private val positionalAdjustments = mapOf<String, Double>(
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

fun computePrice(mlbPlayer: MLBPlayer, lgWeightOnBase: Double, sumLeaguePlateAppearances: Double): Double {
    val avg50yrRPW = 9.757
    // Note -- The collateralization multiplier is meant to scale the calculated WAR price, this may be needed in the future, or further down the stack
    // collateralizationMultiplier = 1000
    val battingRuns = (((((mlbPlayer.plateAppearances * mlbPlayer.weightedOnBasePercentage)) - lgWeightOnBase)) / 1.25)
    val baseRunningRuns = (mlbPlayer.stolenBases * 0.2)
    val fieldingRuns = (((mlbPlayer.errors * -10)) / (mlbPlayer.games * 9))
    val positionalAdjustments = (((mlbPlayer.games * 9) * (positionalAdjustments[mlbPlayer.position] ?: 0.0))) / 1458.0
    val replacementRuns = (mlbPlayer.plateAppearances * 5561.49) / sumLeaguePlateAppearances

    val statsNumerator = battingRuns + baseRunningRuns + fieldingRuns + positionalAdjustments + replacementRuns
    return statsNumerator / avg50yrRPW //WAR price
}

