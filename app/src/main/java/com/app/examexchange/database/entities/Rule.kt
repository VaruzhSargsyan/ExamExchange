package com.app.examexchange.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_rules")
data class Rule (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "description") val name: String,
    @ColumnInfo(name = "sql_script") val sql_script: String
) {
    constructor(name: String, sql_script: String) : this(0, name, sql_script)
}

private val rule1 = Rule("The first five currency exchanges are free of charge",
    "SELECT CAST(CASE WHEN COUNT(id) <= 5 THEN 1 ELSE 0 END AS BIT) FROM table_exchanges;")
fun getRules() = listOf(rule1)