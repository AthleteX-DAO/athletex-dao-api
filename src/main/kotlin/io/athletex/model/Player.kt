package io.athletex.model

import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Serializable
abstract class Player {
    abstract val id: Int
    abstract val name: String
    abstract val price: Double
    abstract val timestamp: String
}

fun wasRecordedDuringBadEntryTime(timestamp: String): Boolean {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
    val fromTime = LocalDateTime.parse("2022-08-29 07:10:00.000000", formatter)
    val untilTime = LocalDateTime.parse("2022-08-29 07:20:00.000000", formatter)
    val timeRecorded = LocalDateTime.parse(timestamp, formatter)
    return timeRecorded.isAfter(fromTime) && timeRecorded.isBefore(untilTime)
}