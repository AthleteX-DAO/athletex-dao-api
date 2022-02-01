package io.athletex.db.models

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp
import java.time.Instant


object NFLPlayers : Table("nfl") {
    val name : Column<String> = text("name")
    val id : Column<String> = text("id").autoIncrement()
    val team : Column<String>  = text("team")
    val position : Column<String>   = text("position")
    val passingYards : Column<Double>  = double("passingYards")
    val passingTouchdowns : Column<Double> = double("passingTouchdowns")
    val reception : Column<Double> = double("reception")
    val receiveYards : Column<Double> = double("receiveYards")
    val receiveTouch : Column<Double> = double("receiveTouch")
    val rushingYards : Column<Double> = double("rushingYards")
    val offensiveSnapsPlayed : Column<Double> = double("OffensiveSnapsPlayed")
    val defensiveSnapsPlayed : Column<Double> = double("DefensiveSnapsPlayed")
    val price : Column<Double> = double("price")
    val timestamp : Column<Instant>  = timestamp("timestamp")
}

