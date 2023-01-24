package com.app.examexchange.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_rules")
data class Rule (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "description") val name: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "type") val type: String
) {
    constructor(name: String, sql_script: String, type: String)
            : this(0, name, sql_script, type)
}

object RuleTypes {
    const val SCRIPT_RUN_ON_EXCHANGE = "Database script for Exchange table"
    const val DEFAULT_FEE = "Default fee value"
    const val CONVERSION_MAXIMAL_LIMIT_IN_EURO = "Conversion limit calculation"
}

private val rule1 = Rule("The first five currency exchanges are free of charge.",
    "SELECT CAST(CASE WHEN COUNT(id) < 5 THEN 1 ELSE 0 END AS BIT) FROM table_exchanges;",
        RuleTypes.SCRIPT_RUN_ON_EXCHANGE)
private val rule2 = Rule("Every tenth conversion is free.",
    "SELECT CAST(CASE WHEN COUNT(id) % 3 = 0 THEN 1 ELSE 0 END AS BIT) FROM table_exchanges;",
        RuleTypes.SCRIPT_RUN_ON_EXCHANGE)
private val rule3 = Rule("Default fee value is equal to 0.7%.",
    "0.007",
        RuleTypes.DEFAULT_FEE)
private val rule4 = Rule("Conversion of up to 200 Euros is free of charge etc.",
    "SELECT CAST(CASE WHEN MOD(COUNT(id), 10) = 0 THEN 1 ELSE 0 END AS BIT) FROM table_exchanges;",
    RuleTypes.CONVERSION_MAXIMAL_LIMIT_IN_EURO)
fun getRules() = listOf(rule1, rule2, rule3, rule4)