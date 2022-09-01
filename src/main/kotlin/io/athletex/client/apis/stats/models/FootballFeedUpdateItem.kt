package io.athletex.client.apis.stats.models
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName

@Serializable
data class FootballFeedUpdateItem(
    @SerialName("PlayerID")
    val playerId: Double,
    @SerialName("SeasonType")
    val seasonType: Double,
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
    @SerialName("OffensiveTouchdowns")
    val offensiveTouchdowns: Double,
    @SerialName("DefensiveTouchdowns")
    val defensiveTouchdowns: Double,
    @SerialName("SpecialTeamsTouchdowns")
    val specialTeamsTouchdowns: Double,
    @SerialName("Touchdowns")
    val touchdowns: Int,
    @SerialName("FantasyPosition")
    val fantasyPosition: Double,
    @SerialName("FieldGoalPercentage")
    val fieldGoalPercentage: String,
    @SerialName("PlayerSeasonID")
    val playerSeasonID: String,
    @SerialName("FumblesOwnRecoveries")
    val fumblesOwnRecoveries: Double,
    @SerialName("FumblesOutOfBounds")
    val fumblesOutOfBounds: Double,
    @SerialName("KickReturnFairCatches")
    val kickReturnFairCatches: Double,
    @SerialName("PuntReturnFairCatches")
    val puntReturnFairCatches: Double,
    @SerialName("PuntTouchbacks")
    val puntTouchbacks: Double,
    @SerialName("PuntInside20")
    val puntInside20: Double,
    @SerialName("PuntNetAverage")
    val puntNetAverage: Double,
    @SerialName("ExtraPointsAttempted")
    val extraPointsAttempted: Double,
    @SerialName("BlockedKickReturnTouchdowns")
    val blockedKickReturnTouchdowns: Double,
    @SerialName("FieldGoalReturnTouchdowns")
    val fieldGoalReturnTouchdowns: Double,
    @SerialName("Safeties")
    val safeties: Double,
    @SerialName("FieldGoalsHadBlocked")
    val fieldGoalsHadBlocked: Double,
    @SerialName("PuntsHadBlocked")
    val puntsHadBlocked: Double,
    @SerialName("ExtraPointsHadBlocked")
    val extraPointsHadBlocked: Double,
    @SerialName("PuntLong")
    val puntLong: Double,
    @SerialName("BlockedKickReturnYards")
    val blockedKickReturnYards: Double,
    @SerialName("FieldGoalReturnYards")
    val fieldGoalReturnYards: Double,
    @SerialName("PuntNetYards")
    val puntNetYards: Double,
    @SerialName("SpecialTeamsFumblesForced")
    val specialTeamsFumblesForced: Double,
    @SerialName("SpecialTeamsFumblesRecovered")
    val specialTeamsFumblesRecovered: Double,
    @SerialName("MiscFumblesForced")
    val miscFumblesForced: Double,
    @SerialName("MiscFumblesRecovered")
    val miscFumblesRecovered: Double,
    @SerialName("ShortName")
    val shortName: String,
    @SerialName("SafetiesAllowed")
    val safetiesAllowed: Double,
    @SerialName("Temperature")
    val temperature: Double,
    @SerialName("Humidity")
    val humidity: Double,
    @SerialName("WindSpeed")
    val windSpeed: Double,
    @SerialName("OffensiveSnapsPlayed")
    val offensiveSnapsPlayed: Double,
    @SerialName("DefensiveSnapsPlayed")
    val defensiveSnapsPlayed: Double,
    @SerialName("SpecialTeamsSnapsPlayed")
    val specialTeamsSnapsPlayed: Double,
    @SerialName("OffensiveTeamSnaps")
    val offensiveTeamSnaps: Double,
    @SerialName("DefensiveTeamSnaps")
    val defensiveTeamSnaps: Double,
    @SerialName("SpecialTeamsTeamSnaps")
    val specialTeamsTeamSnaps: Double,
    @SerialName("TwoPointConversionReturns")
    val twoPointConversionReturns: Double,
    @SerialName("FantasyPointsFanDuel")
    val fantasyPointsFanDuel: Double,
    @SerialName("FieldGoalsMade0to19")
    val fieldGoalsMade0to19: Double,
    @SerialName("FieldGoalsMade20to29")
    val fieldGoalsMade20to29: Double,
    @SerialName("FieldGoalsMade30to39")
    val fieldGoalsMade30to39: Double,
    @SerialName("FieldGoalsMade40to49")
    val fieldGoalsMade40to49: Double,
    @SerialName("FieldGoalsMade50Plus")
    val fieldGoalsMade50Plus: Double,
    @SerialName("FantasyPointsDraftKings")
    val fantasyPointsDraftKings: Double,
    @SerialName("FantasyPointsYahoo")
    val fantasyPointsYahoo: Double,
    @SerialName("TeamID")
    val teamID: Double,
    @SerialName("GlobalTeamID")
    val globalTeamID: Double,
    @SerialName("FantasyPointsFantasyDraft")
)
