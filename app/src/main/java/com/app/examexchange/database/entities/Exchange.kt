package com.app.examexchange.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_exchanges")
data class Exchange (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "currency_from") val currencyFrom: String,
    @ColumnInfo(name = "currency_to") val currencyTo: String,
    @ColumnInfo(name = "rate") val rate: Float,
    @ColumnInfo(name = "sum") val sum: Float,
    @ColumnInfo(name = "fee") val fee: Float,
    @ColumnInfo(name = "created_at") val createdAt: Long = System.currentTimeMillis()
)