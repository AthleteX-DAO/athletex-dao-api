package io.athletex.client.apis.stats.models
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName

@Serializable
data class FootballFeedUpdateItem(
    @SerialName("PlayerID")
    val playerId: Int,
    @SerialName("SeasonType")
    val seasonType: Double?,
    @SerialName("Season")
    val season: Double?,
    @SerialName("Team")
    val team: String,
    @SerialName("Number")
    val number: Double?,
    @SerialName("Name")
    val name: String,
    @SerialName("Position")
    val position: String,
    @SerialName("PositionCategory")
    val positionCategory: String,
    @SerialName("Activated")
    val activated: Double?,
    @SerialName("Played")
    val played: Double?,
    @SerialName("Started")
    val started: Double?,
    @SerialName("PassingAttempts")
    val passingAttempts: Double?,
    @SerialName("PassingCompletions")
    val passingCompletions: Double?,
    @SerialName("PassingYards")
    val passingYards: Double?,
    @SerialName("PassingCompletionPercentage")
    val passingCompletionPercentage: Double?,
    @SerialName("PassingYardsPerAttempt")
    val passingYardsPerAttempt: Double?,
    @SerialName("PassingYardsPerCompletion")
    val passingYardsPerCompletion: Double?,
    @SerialName("PassingTouchdowns")
    val passingTouchdowns: Double?,
    @SerialName("PassingInterceptions")
    val passingInterceptions: Double?,
    @SerialName("PassingRating")
    val passingRating: Double?,
    @SerialName("PassingLong")
    val passingLong: Double?,
    @SerialName("PassingSacks")
    val passingSacks: Double?,
    @SerialName("PassingSackYards")
    val passingSackYards: Double?,
    @SerialName("RushingAttempts")
    val rushingAttempts: Double?,
    @SerialName("RushingYards")
    val rushingYards: Double?,
    @SerialName("RushingYardsPerAttempt")
    val rushingYardsPerAttempt: Double?,
    @SerialName("RushingTouchdowns")
    val rushingTouchdowns: Double?,
    @SerialName("RushingLong")
    val rushingLong: Double?,
    @SerialName("ReceivingTargets")
    val receivingTargets: Double?,
    @SerialName("Receptions")
    val receptions: Double?,
    @SerialName("ReceivingYards")
    val receivingYards: Double?,
    @SerialName("ReceivingYardsPerReception")
    val receivingYardsPerReception: Double?,
    @SerialName("ReceivingTouchdowns")
    val receivingTouchdowns: Double?,
    @SerialName("ReceivingLong")
    val receivingLong: Double?,
    @SerialName("Fumbles")
    val fumbles: Double?,
    @SerialName("FumblesLost")
    val fumblesLost: Double?,
    @SerialName("FumblesForced")
    val fumblesForced: Double?,
    @SerialName("FumblesRecovered")
    val fumblesRecovered: Double?,
    @SerialName("FumbleReturnYards")
    val fumbleReturnYards: Double?,
    @SerialName("FumbleReturnTouchdowns")
    val fumbleReturnTouchdowns: Double?,
    @SerialName("FantasyPoints")
    val fantasyPoints: Double?,
    @SerialName("FantasyPointsPPR")
    val fantasyPointsPPR: Double?,
    @SerialName("ReceptionPercentage")
    val receptionPercentage: Double?,
    @SerialName("ReceivingYardsPerTarget")
    val receivingYardsPerTarget: Double?,
    @SerialName("Tackles")
    val tackles: Double?,
    @SerialName("OffensiveTouchdowns")
    val offensiveTouchdowns: Double?,
    @SerialName("DefensiveTouchdowns")
    val defensiveTouchdowns: Double?,
    @SerialName("SpecialTeamsTouchdowns")
    val specialTeamsTouchdowns: Double?,
    @SerialName("Touchdowns")
    val touchdowns: Int,
    @SerialName("OffensiveSnapsPlayed")
    val offensiveSnapsPlayed: Double?,
    @SerialName("DefensiveSnapsPlayed")
    val defensiveSnapsPlayed: Double?,
    @SerialName("TeamID")
    val teamID: Double?,
)
