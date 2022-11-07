package io.athletex.client.formulas

import io.athletex.client.apis.stats.models.BasketballFeedUpdateItem

val nbaStatMult = mapOf(
    "Point" to 1.0,
    "3PM" to 1.0,
    "FGA" to -1.0,
    "FGM" to 2.0,
    "FTA" to -1.0,
    "FTM" to 1.0,
    "REB" to 1.0,
    "AST" to 2.0,
    "STL" to 4.0,
    "BLK" to 4.0,
    "TOV" to -2.0,
)

fun computeNBAPrice(feedUpdateItem: BasketballFeedUpdateItem): Double {
    var fantasyPoints: Double = (((feedUpdateItem.points ?: 0.0)            * (nbaStatMult["Point"] ?: 0.0))
                    +((feedUpdateItem.threePointersMade ?: 0.0)     * (nbaStatMult["3PM"]   ?: 0.0))
                    +((feedUpdateItem.fieldGoalsAttempted ?: 0.0)   * (nbaStatMult["FGA"]   ?: 0.0))
                    +((feedUpdateItem.fieldGoalsMade ?: 0.0)        * (nbaStatMult["FGM"]   ?: 0.0))
                    +((feedUpdateItem.freeThrowsAttempted ?: 0.0)   * (nbaStatMult["FTA"]   ?: 0.0))
                    +((feedUpdateItem.freeThrowsMade ?: 0.0)        * (nbaStatMult["FTM"]   ?: 0.0))
                    +((feedUpdateItem.rebounds ?: 0.0)              * (nbaStatMult["REB"]   ?: 0.0))
                    +((feedUpdateItem.assists ?: 0.0)               * (nbaStatMult["AST"]   ?: 0.0))
                    +((feedUpdateItem.steals ?: 0.0)                * (nbaStatMult["STL"]   ?: 0.0))
                    +((feedUpdateItem.blocks ?: 0.0)                * (nbaStatMult["BLK"]   ?: 0.0))
                    +((feedUpdateItem.turnovers ?: 0.0)             * (nbaStatMult["TOV"]   ?: 0.0))
    )
    
    //fantasy points per minute normalized (* 100), for it to be between 0-1000AX
    var minutes = feedUpdateItem.minutesPlayed ?: 1.0
    if (minutes == 0.0) minutes = 1.0

    var fppmN: Double = (fantasyPoints / minutes) * 100.0

    return fppmN
}
