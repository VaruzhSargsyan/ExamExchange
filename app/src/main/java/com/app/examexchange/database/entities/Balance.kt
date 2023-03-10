package com.app.examexchange.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_balance")
data class Balance (
    @PrimaryKey
    @ColumnInfo(name = "currency") val currency: String,
    @ColumnInfo(name = "value") val value: Float
) {
    fun getFormattedValueString() = String.format("%.2f", value)
}
