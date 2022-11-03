package io.athletex.client.formulas

import io.athletex.client.apis.stats.models.BasketballFeedUpdateItem

val nbaStatMult = mapOf(
    "Point" to 1,
    "3PM" to 1,
    "FGA" to -1,
    "FGM" to 2,
    "FTA" to -1,
    "FTM" to 1,
    "REB" to 1,
    "AST" to 2,
    "STL" to 4,
    "BLK" to 4,
    "TOV" to -2,
)

fun computeNBAPrice(feedUpdateItem: BasketballFeedUpdateItem): Double {
    var fantasyPoints: Double = (((feedUpdateItem.points ?: 0.0) * nbaStatMult["Point"])
                    +((feedUpdateItem.threePointersMade ?: 0.0) * nbaStatMult["3PM"])
                    +((feedUpdateItem.fieldGoalsAttempted ?: 0.0) * nbaStatMult["FGA"])
                    +((feedUpdateItem.fieldGoalsMade ?: 0.0) * nbaStatMult["FGM"])
                    +((feedUpdateItem.freeThrowsAttempted ?: 0.0) * nbaStatMult["FTA"])
                    +((feedUpdateItem.freeThrowsMade ?: 0.0) * nbaStatMult["FTM"])
                    +((feedUpdateItem.rebounds ?: 0.0) * nbaStatMult["REB"])
                    +((feedUpdateItem.assists ?: 0.0) * nbaStatMult["AST"])
                    +((feedUpdateItem.steals ?: 0.0) * nbaStatMult["STL"])
                    +((feedUpdateItem.blocks ?: 0.0) * nbaStatMult["BLK"])
                    +((feedUpdateItem.turnovers ?: 0.0) * nbaStatMult["TOV"])
    )
    
    //fantasy points per minute normalized (* 100), for it to be between 0-1000AX
    var fppmN: Double = (fantasyPoints / feedUpdateItem.minutesPlayed) * 100

    return fppmN
}
