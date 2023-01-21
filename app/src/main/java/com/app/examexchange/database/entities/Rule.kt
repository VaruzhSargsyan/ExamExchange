package com.app.examexchange.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_rules")
data class Rule (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "description") val name: String,
    @ColumnInfo(name = "sql_script") val rate: Float
)