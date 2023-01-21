package com.app.examexchange.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_balance")
data class Balance (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "currency") val currency: Int,
    @ColumnInfo(name = "value") val value: Float
)