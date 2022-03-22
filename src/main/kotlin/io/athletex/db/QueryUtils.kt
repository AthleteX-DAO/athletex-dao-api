package io.athletex.db

enum class Table(val tableName: String) {
    NFL("nfl"),
    MLB("mlb")
}

fun queryLatestPlayerRecordsNameOrdered(table: Table) =
    "SELECT * FROM ${table.tableName} " +
            "LATEST BY name " +
            "ORDER by name"