package com.app.examexchange.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_exchanges")
data class Exchange (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "currency_from") val currencyFrom: String = "",
    @ColumnInfo(name = "currency_to") val currencyTo: String = "",
    @ColumnInfo(name = "rate_to") val rateTo: Float = 0f,
    @ColumnInfo(name = "rate_from") val rateFrom: Float = 0f,
    @ColumnInfo(name = "sum_from") val sumFrom: Float = 0f,
    @ColumnInfo(name = "sum_to") val sumTo: Float = 0f,
    @ColumnInfo(name = "feeRate") val feeRate: Float = 0f,
    @ColumnInfo(name = "created_at") val createdAt: Long = System.currentTimeMillis()
) {
//    fun updateFrom(sum: Float, currency: Currency) {
//
//    }
//    fun updateTo(sum: Float, currency: Currency) {
//
//    }
}

//fun getDefaultExchange() = Exchange(0, "EUR", "USD", 0f, 0f, 0.7f)