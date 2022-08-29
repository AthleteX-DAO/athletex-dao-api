package io.athletex.client.apis.stats.models
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName

@Serializable
data class BaseballFeedUpdateItem(
    @SerialName("AtBats")
    val atBats: Double,
    @SerialName("BallsInPlay")
    val ballsInPlay: Double,
    @SerialName("BattingAverage")
    val battingAverage: Double,
    @SerialName("BattingAverageOnBallsInPlay")
    val battingAverageOnBallsInPlay: Double,
    @SerialName("BattingOrderConfirmed")
    val battingOrderConfirmed: Boolean,
    @SerialName("CaughtStealing")
    val caughtStealing: Double,
    @SerialName("DoublePlays")
    val doublePlays: Double,
    @SerialName("Doubles")
    val doubles: Double,
    @SerialName("EarnedRunAverage")
    val earnedRunAverage: Double,
    @SerialName("Errors")
    val errors: Double,
    @SerialName("FantasyPoints")
    val fantasyPoints: Double,
    @SerialName("FantasyPointsBatting")
    val fantasyPointsBatting: Double,
    @SerialName("FantasyPointsDraftKings")
    val fantasyPointsDraftKings: Double,
    @SerialName("FantasyPointsFanDuel")
    val fantasyPointsFanDuel: Double,
    @SerialName("FantasyPointsFantasyDraft")
    val fantasyPointsFantasyDraft: Double,
    @SerialName("FantasyPointsPitching")
    val fantasyPointsPitching: Double,
    @SerialName("FantasyPointsYahoo")
    val fantasyPointsYahoo: Double,
    @SerialName("FieldingIndependentPitching")
    val fieldingIndependentPitching: Double,
    @SerialName("FlyOuts")
    val flyOuts: Double,
    @SerialName("Games")
    val games: Double,
    @SerialName("GlobalTeamID")
    val globalTeamID: Double,
    @SerialName("GrandSlams")
    val grandSlams: Double,
    @SerialName("GroundIntoDoublePlay")
    val groundIntoDoublePlay: Double,
    @SerialName("GroundOuts")
    val groundOuts: Double,
    @SerialName("HitByPitch")
    val hitByPitch: Double,
    @SerialName("Hits")
    val hits: Double,
    @SerialName("HomeRuns")
    val homeRuns: Double,
    @SerialName("InningsPitchedDecimal")
    val inningsPitchedDecimal: Double,
    @SerialName("InningsPitchedFull")
    val inningsPitchedFull: Double,
    @SerialName("InningsPitchedOuts")
    val inningsPitchedOuts: Double,
    @SerialName("IntentionalWalks")
    val intentionalWalks: Double,
    @SerialName("IsolatedPower")
    val isolatedPower: Double,
    @SerialName("LeftOnBase")
    val leftOnBase: Double,
    @SerialName("LineOuts")
    val lineOuts: Double,
    @SerialName("Losses")
    val losses: Double,
    @SerialName("Name")
    val name: String,
    @SerialName("OnBasePercentage")
    val onBasePercentage: Double,
    @SerialName("OnBasePlusSlugging")
    val onBasePlusSlugging: Double,
    @SerialName("Outs")
    val outs: Double,
    @SerialName("PitchesSeen")
    val pitchesSeen: Double,
    @SerialName("PitchesThrown")
    val pitchesThrown: Double,
    @SerialName("PitchesThrownStrikes")
    val pitchesThrownStrikes: Double,
    @SerialName("PitchingBallsInPlay")
    val pitchingBallsInPlay: Double,
    @SerialName("PitchingBattingAverageAgainst")
    val pitchingBattingAverageAgainst: Double,
    @SerialName("PitchingBattingAverageOnBallsInPlay")
    val pitchingBattingAverageOnBallsInPlay: Double,
    @SerialName("PitchingBlownSaves")
    val pitchingBlownSaves: Double,
    @SerialName("PitchingCatchersInterference")
    val pitchingCatchersInterference: Double,
    @SerialName("PitchingCompleteGames")
    val pitchingCompleteGames: Double,
    @SerialName("PitchingDoublePlays")
    val pitchingDoublePlays: Double,
    @SerialName("PitchingDoubles")
    val pitchingDoubles: Double,
    @SerialName("PitchingEarnedRuns")
    val pitchingEarnedRuns: Double,
    @SerialName("PitchingFlyOuts")
    val pitchingFlyOuts: Double,
    @SerialName("PitchingGrandSlams")
    val pitchingGrandSlams: Double,
    @SerialName("PitchingGroundIntoDoublePlay")
    val pitchingGroundIntoDoublePlay: Double,
    @SerialName("PitchingGroundOuts")
    val pitchingGroundOuts: Double,
    @SerialName("PitchingHitByPitch")
    val pitchingHitByPitch: Double,
    @SerialName("PitchingHits")
    val pitchingHits: Double,
    @SerialName("PitchingHolds")
    val pitchingHolds: Double,
    @SerialName("PitchingHomeRuns")
    val pitchingHomeRuns: Double,
    @SerialName("PitchingIntentionalWalks")
    val pitchingIntentionalWalks: Double,
    @SerialName("PitchingLineOuts")
    val pitchingLineOuts: Double,
    @SerialName("PitchingNoHitters")
    val pitchingNoHitters: Double,
    @SerialName("PitchingOnBasePercentage")
    val pitchingOnBasePercentage: Double,
    @SerialName("PitchingOnBasePlusSlugging")
    val pitchingOnBasePlusSlugging: Double,
    @SerialName("PitchingPerfectGames")
    val pitchingPerfectGames: Double,
    @SerialName("PitchingPlateAppearances")
    val pitchingPlateAppearances: Double,
    @SerialName("PitchingPopOuts")
    val pitchingPopOuts: Double,
    @SerialName("PitchingQualityStarts")
    val pitchingQualityStarts: Double,
    @SerialName("PitchingReachedOnError")
    val pitchingReachedOnError: Double,
    @SerialName("PitchingRuns")
    val pitchingRuns: Double,
    @SerialName("PitchingSacrificeFlies")
    val pitchingSacrificeFlies: Double,
    @SerialName("PitchingSacrifices")
    val pitchingSacrifices: Double,
    @SerialName("PitchingShutOuts")
    val pitchingShutOuts: Double,
    @SerialName("PitchingSingles")
    val pitchingSingles: Double,
    @SerialName("PitchingSluggingPercentage")
    val pitchingSluggingPercentage: Double,
    @SerialName("PitchingStrikeouts")
    val pitchingStrikeouts: Double,
    @SerialName("PitchingStrikeoutsPerNineInnings")
    val pitchingStrikeoutsPerNineInnings: Double,
    @SerialName("PitchingTotalBases")
    val pitchingTotalBases: Double,
    @SerialName("PitchingTriples")
    val pitchingTriples: Double,
    @SerialName("PitchingWalks")
    val pitchingWalks: Double,
    @SerialName("PitchingWalksPerNineInnings")
    val pitchingWalksPerNineInnings: Double,
    @SerialName("PitchingWeightedOnBasePercentage")
    val pitchingWeightedOnBasePercentage: Double,
    @SerialName("PlateAppearances")
    val plateAppearances: Double,
    @SerialName("PlayerID")
    val playerID: Int,
    @SerialName("PopOuts")
    val popOuts: Double,
    @SerialName("Position")
    val position: String,
    @SerialName("PositionCategory")
    val positionCategory: String,
    @SerialName("ReachedOnError")
    val reachedOnError: Double,
    @SerialName("Runs")
    val runs: Double,
    @SerialName("RunsBattedIn")
    val runsBattedIn: Double,
    @SerialName("SacrificeFlies")
    val sacrificeFlies: Double,
    @SerialName("Sacrifices")
    val sacrifices: Double,
    @SerialName("Saves")
    val saves: Double,
    @SerialName("Season")
    val season: Double,
    @SerialName("SeasonType")
    val seasonType: Double,
    @SerialName("Singles")
    val singles: Double,
    @SerialName("SluggingPercentage")
    val sluggingPercentage: Double,
    @SerialName("Started")
    val started: Double,
    @SerialName("StatID")
    val statID: Double,
    @SerialName("StolenBases")
    val stolenBases: Double,
    @SerialName("Strikeouts")
    val strikeouts: Double,
    @SerialName("Team")
    val team: String,
    @SerialName("TeamID")
    val teamID: Double,
    @SerialName("TotalBases")
    val totalBases: Double,
    @SerialName("TotalOutsPitched")
    val totalOutsPitched: Double,
    @SerialName("Triples")
    val triples: Double,
    @SerialName("Updated")
    val updated: String,
    @SerialName("Walks")
    val walks: Double,
    @SerialName("WalksHitsPerInningsPitched")
    val walksHitsPerInningsPitched: Double,
    @SerialName("WeightedOnBasePercentage")
    val weightedOnBasePercentage: Double,
    @SerialName("Wins")
    val wins: Double
)