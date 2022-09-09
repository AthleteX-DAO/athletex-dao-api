package io.athletex.client.apis.stats.models
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName

@Serializable
data class FootballFeedUpdateItem(
    @SerialName("PlayerID")
    val playerId: Double,
    @SerialName("Season")
    val season: Double,
    @SerialName("Team")
    val team: String,
    @SerialName("Number")
    val number: Double,
    @SerialName("Name")
    val name: String,
    @SerialName("Position")
    val position: String,
    @SerialName("PositionCategory")
    val positionCategory: String,
    @SerialName("Activated")
    val activated: Double,
    @SerialName("Played")
    val played: Double,
    @SerialName("Started")
    val started: Double,
    @SerialName("PassingAttempts")
    val passingAttempts: Double,
    @SerialName("PassingCompletions")
    val passingCompletions: Double,
    @SerialName("PassingYards")
    val passingYards: Double,
    @SerialName("PassingCompletionPercentage")
    val passingCompletionPercentage: Double,
    @SerialName("PassingYardsPerAttempt")
    val passingYardsPerAttempt: Double,
    @SerialName("PassingYardsPerCompletion")
    val passingYardsPerCompletion: Double,
    @SerialName("PassingTouchdowns")
    val passingTouchdowns: Double,
    @SerialName("PassingInterceptions")
    val passingInterceptions: Double,
    @SerialName("PassingRating")
    val passingRating: Double,
    @SerialName("PassingLong")
    val passingLong: Double,
    @SerialName("PassingSacks")
    val passingSacks: Double,
    @SerialName("PassingSackYards")
    val passingSackYards: Double,
    @SerialName("RushingAttempts")
    val rushingAttempts: Double,
    @SerialName("RushingYards")
    val rushingYards: Double,
    @SerialName("RushingYardsPerAttempt")
    val rushingYardsPerAttempt: Double,
    @SerialName("RushingTouchdowns")
    val rushingTouchdowns: Double,
    @SerialName("RushingLong")
    val rushingLong: Double,
    @SerialName("ReceivingTargets")
    val receivingTargets: Double,
    @SerialName("Receptions")
    val receptions: Double,
    @SerialName("ReceivingYards")
    val receivingYards: Double,
    @SerialName("ReceivingYardsPerReception")
    val receivingYardsPerReception: Double,
    @SerialName("ReceivingTouchdowns")
    val receivingTouchdowns: Double,
    @SerialName("ReceivingLong")
    val receivingLong: Double,
    @SerialName("Fumbles")
    val fumbles: Double,
    @SerialName("FumblesLost")
    val fumblesLost: Double,
    @SerialName("PuntReturns")
    val puntReturns: Double,
    @SerialName("PuntReturnYards")
    val puntReturnYards: Double,
    @SerialName("PuntReturnYardsPerAttempt")
    val puntReturnYardsPerAttempt: Double,
    @SerialName("PuntReturnTouchdowns")
    val puntReturnTouchdowns: Double,
    @SerialName("PuntReturnLong")
    val puntReturnLong: Double,
    @SerialName("KickReturns")
    val kickReturns: Double,
    @SerialName("KickReturnYards")
    val kickReturnYards: Double,
    @SerialName("KickReturnYardsPerAttempt")
    val kickReturnYardsPerAttempt: Double,
    @SerialName("KickReturnTouchdowns")
    val kickReturnTouchdowns: Double,
    @SerialName("KickReturnLong")
    val kickReturnLong: Double,
    @SerialName("SoloTackles")
    val soloTackles: Double,
    @SerialName("AssistedTackles")
    val assistedTackles: Double,
    @SerialName("TacklesForLoss")
    val tacklesForLoss: Double,
    @SerialName("Sacks")
    val sacks: Double,
    @SerialName("SackYards")
    val sackYards: Double,
    @SerialName("QuarterbackHits")
    val quarterbackHits: Double,
    @SerialName("PassesDefended")
    val passesDefended: Double,
    @SerialName("FumblesForced")
    val fumblesForced: Double,
    @SerialName("FumblesRecovered")
    val fumblesRecovered: Double,
    @SerialName("FumbleReturnYards")
    val fumbleReturnYards: Double,
    @SerialName("FumbleReturnTouchdowns")
    val fumbleReturnTouchdowns: Double,
    @SerialName("Interceptions")
    val interceptions: Double,
    @SerialName("InterceptionReturnYards")
    val interceptionReturnYards: Double,
    @SerialName("InterceptionReturnTouchdowns")
    val interceptionReturnTouchdowns: Double,
    @SerialName("BlockedKicks")
    val blockedKicks: Double,
    @SerialName("SpecialTeamsSoloTackles")
    val specialTeamsSoloTackles: Double,
    @SerialName("SpecialTeamsAssistedTackles")
    val specialTeamsAssistedTackles: Double,
    @SerialName("MiscSoloTackles")
    val miscSoloTackles: Double,
    @SerialName("MiscAssistedTackles")
    val miscAssistedTackles: Double,
    @SerialName("Punts")
    val punts: Double,
    @SerialName("PuntYards")
    val puntYards: Double,
    @SerialName("PuntAverage")
    val puntAverage: Double,
    @SerialName("FieldGoalsAttempted")
    val fieldGoalsAttempted: Double,
    @SerialName("FieldGoalsMade")
    val fieldGoalsMade: Double,
    @SerialName("FieldGoalsLongestMade")
    val fieldGoalsLongestMade: Double,
    @SerialName("ExtraPointsMade")
    val extraPointsMade: Double,
    @SerialName("TwoPointConversionPasses")
    val twoPointConversionPasses: Double,
    @SerialName("TwoPointConversionRuns")
    val twoPointConversionRuns: Double,
    @SerialName("TwoPointConversionReceptions")
    val twoPointConversionReceptions: Double,
    @SerialName("FantasyPoints")
    val fantasyPoints: Double,
    @SerialName("FantasyPointsPPR")
    val fantasyPointsPPR: Double,
    @SerialName("ReceptionPercentage")
    val receptionPercentage: Double,
    @SerialName("ReceivingYardsPerTarget")
    val receivingYardsPerTarget: Double,
    @SerialName("Tackles")
    val tackles: Double,
    @SerialName("Touchdowns")
    val touchdowns: Int,
    @SerialName("OffensiveSnapsPlayed")
    val offensiveSnapsPlayed: Double,
    @SerialName("DefensiveSnapsPlayed")
    val defensiveSnapsPlayed: Double,
)
