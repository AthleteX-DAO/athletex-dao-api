package io.athletex.client.apis.stats.models
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName

@Serializable
data class BasketballFeedUpdateItem(
    @SerialName("StatID")
    val statID: Int?,
    @SerialName("TeamID")
    val teamID: Int?,
    @SerialName("PlayerID")
    val playerID: Int?,
    @SerialName("SeasonType")
    val seasonType: Int?,
    @SerialName("Season")
    val season: Int?,
    @SerialName("Name")
    val name: String?,
    @SerialName("Team")
    val team: String?,
    @SerialName("Position")
    val position: String?,
    @SerialName("Started")
    val started: Int?,
    @SerialName("GlobalTeamID")
    val globalTeamID: Int?,
    @SerialName("Updated")
    val updated: String?,
    @SerialName("Games")
    val games: Int?,
    @SerialName("FantasyPoints")
    val fantasyPoints: Double?,
    @SerialName("Minutes")
    val minutesPlayed: Int?,
    @SerialName("Seconds")
    val seconds: Int?,
    @SerialName("FieldGoalsMade")
    val fieldGoalsMade: Double?,
    @SerialName("FieldGoalsAttempted")
    val fieldGoalsAttempted: Double?,
    @SerialName("FieldGoalsPercentage")
    val fieldGoalsPercentage: Double?,
    @SerialName("EffectiveFieldGoalsPercentage")
    val effectiveFieldGoalsPercentage: Double?,
    @SerialName("TwoPointersMade")
    val twoPointersMade: Double?,
    @SerialName("TwoPointersAttempted")
    val twoPointersAttempted: Double?,
    @SerialName("TwoPointersPercentage")
    val twoPointersPercentage: Double?,
    @SerialName("ThreePointersMade")
    val threePointersMade: Double?,
    @SerialName("ThreePointersAttempted")
    val threePointersAttempted: Double?,
    @SerialName("ThreePointersPercentage")
    val threePointersPercentage: Double?,
    @SerialName("FreeThrowsMade")
    val freeThrowsMade: Double?,
    @SerialName("FreeThrowsAttempted")
    val freeThrowsAttempted: Double?,
    @SerialName("FreeThrowsPercentage")
    val freeThrowsPercentage: Double?,
    @SerialName("OffensiveRebounds")
    val offensiveRebounds: Double?,
    @SerialName("DefensiveRebounds")
    val defensiveRebounds: Double?,
    @SerialName("Rebounds")
    val rebounds: Double?,
    @SerialName("OffensiveReboundsPercentage")
    val offensiveReboundsPercentage: Double?,
    @SerialName("DefensiveReboundsPercentage")
    val defensiveReboundsPercentage: Double?,
    @SerialName("TotalReboundsPercentage")
    val totalReboundsPercentage: Double?,
    @SerialName("Assists")
    val assists: Double?,
    @SerialName("Steals")
    val steals: Double?,
    @SerialName("BlockedShots")
    val blocks: Double?,
    @SerialName("Turnovers")
    val turnovers: Double?,
    @SerialName("PersonalFouls")
    val personalFouls: Double?,
    @SerialName("Points")
    val points: Double?,
    @SerialName("TrueShootingAttempts")
    val trueShootingAttempts: Double?,
    @SerialName("TrueShootingPercentage")
    val trueShootingPercentage: Double?,
    @SerialName("PlayerEfficiencyRating")
    val playerEfficiencyRating: Double?,
    @SerialName("AssistsPercentage")
    val assistsPercentage: Double?,
    @SerialName("StealsPercentage")
    val stealsPercentage: Double?,
    @SerialName("BlocksPercentage")
    val blocksPercentage: Double?,
    @SerialName("TurnOversPercentage")
    val turnOversPercentage: Double?,
    @SerialName("UsageRatePercentage")
    val usageRatePercentage: Double?,
    @SerialName("FantasyPointsFanDuel")
    val fantasyPointsFanDuel: Double?,
    @SerialName("FantasyPointsDraftKings")
    val fantasyPointsDraftKings: Double?,
    @SerialName("FantasyPointsYahoo")
    val fantasyPointsYahoo: Double?,
    @SerialName("PlusMinus")
    val plusMinus: Double?,
    @SerialName("DoubleDoubles")
    val doubleDoubles: Double?,
    @SerialName("TripleDoubles")
    val tripleDoubles: Double?,
    @SerialName("FantasyPointsFantasyDraft")
    val fantasyPointsFantasyDraft: Double?,
    @SerialName("IsClosed")
    val isClosed: Boolean?,
    @SerialName("LineupConfirmed")
    val lineupConfirmed: Boolean?,
    @SerialName("LineupStatus")
    val lineupStatus: String?,
)