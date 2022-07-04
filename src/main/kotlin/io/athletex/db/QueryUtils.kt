package io.athletex.db

import io.athletex.routes.payloads.PlayerIds

enum class Table(val tableName: String) {
    NFL("nfl"),
    MLB("mlb")
}

fun queryLatestPlayerRecords(table: Table) =
    "SELECT * FROM ${table.tableName} " +
            "LATEST BY name " +
            "ORDER by name"

fun queryStatsForSinglePlayer(table: Table) =
    "SELECT * FROM ${table.tableName} " +
            "LATEST BY name " +
            "ORDER by timestamp DESC "

fun queryLatestPlayerRecordsByTeam(team: String, table: Table) =
    "SELECT * FROM ${table.tableName} " +
            "LATEST BY name " +
            "WHERE team = '$team' " +
            "ORDER by name"

fun queryLatestPlayerRecordsByPosition(position: String, table: Table) =
    "SELECT * FROM ${table.tableName} " +
            "LATEST BY name " +
            "WHERE position = '$position' " +
            "ORDER by name"

fun queryLatestPlayerRecordsOnTeamByPosition(position: String, team: String, table: Table) =
    "SELECT * FROM ${table.tableName} " +
            "LATEST BY name " +
            "WHERE position = '$position' " +
            "AND team = '$team' " +
            "ORDER by name"

fun queryLatestPlayerRecordsById(playerIds: PlayerIds, table: Table) =
    "SELECT * FROM ${table.tableName} " +
            "LATEST BY name " +
            "WHERE id IN (${playerIds.ids.joinToString(",") { id -> "\'$id\'" }}) " +
            "ORDER by name"

fun queryLatestRecordForSinglePlayer(playerId: Int, targetDate: String? = null, table: Table) =
    "SELECT * FROM ${table.tableName} " +
            "LATEST BY name " +
            "WHERE id = '$playerId' " +
            if(targetDate != null) addTimestampBound(targetDate) else "" +
                    "ORDER by name"

fun queryRecordHistoryForSinglePlayer(playerId: Int, from: String?, until: String?, table: Table) =
    "SELECT * FROM ${table.tableName} " +
            "WHERE id = '$playerId' " +
            addTimeFilter(from, until) +
            "ORDER by timestamp DESC "

private fun addTimeFilter(fromDate: String?, untilDate: String?): String =
    when {
        (!fromDate.isNullOrEmpty() && !untilDate.isNullOrEmpty()) -> addTimestampRange(fromDate, untilDate)
        (!fromDate.isNullOrEmpty()) -> addFromTimestamp(fromDate)
        (!untilDate.isNullOrEmpty()) -> addUntilTimestamp(untilDate)
        else -> ""
    }

private fun addFromTimestamp(fromDate: String): String =
    "AND timestamp > to_timestamp('$fromDate:00:00:00.000000Z', 'yyyy-MM-dd:HH:mm:ss.SSSUUUZ')"

private fun addUntilTimestamp(untilDate: String): String =
    "AND timestamp < to_timestamp('$untilDate:00:00:00.000000Z', 'yyyy-MM-dd:HH:mm:ss.SSSUUUZ')"

private fun addTimestampRange(fromDate: String, untilDate: String): String =
    "AND timestamp " +
            "BETWEEN to_timestamp('$fromDate:00:00:00.000000Z', 'yyyy-MM-dd:HH:mm:ss.SSSUUUZ') " +
            "AND to_timestamp('$untilDate:00:00:00.000000Z', 'yyyy-MM-dd:HH:mm:ss.SSSUUUZ')"

private fun addTimestampBound(targetDate: String): String =
    "AND '$targetDate' = to_str(timestamp, 'yyyy-MM-dd')"
