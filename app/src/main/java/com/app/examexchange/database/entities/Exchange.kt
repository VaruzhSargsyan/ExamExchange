package com.app.examexchange.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_exchanges")

class Exchange(@PrimaryKey(autoGenerate = true) val id: Int = 0,
                @ColumnInfo(name = "currency_sell") val currencySell: String = "",
                @ColumnInfo(name = "currency_receive") val currencyReceive: String = "",
                @ColumnInfo(name = "rate_sell") val rateSell: Float = 0f,
                @ColumnInfo(name = "rate_receive") val rateReceive: Float = 0f,
                @ColumnInfo(name = "sum_sell") val sumSell: Float = 0f,
                @ColumnInfo(name = "sum_receive") val sumReceive: Float = 0f,
                @ColumnInfo(name = "fee_rate") var feeRate: Float = 0f,
                @ColumnInfo(name = "fee_sum") var feeSum: Float = 0f,
                @ColumnInfo(name = "created_at") val createdAt: Long = System.currentTimeMillis()
) {
    constructor(currSell: Currency, currReceive: Currency,
                sumSell: Float, sumReceive: Float,
                feeRate: Float, feeSum: Float)
            : this(0,
        currSell.name, currReceive.name,
        currSell.rate, currReceive.rate,
        sumSell, sumReceive,
        feeRate, feeSum)
    
    fun updateSellBalance(balance: Balance) : Balance {
        return Balance(currency = currencySell, value = balance.value - sumSell - sumSell * feeRate)
    }
    
    fun updateReceiveBalance(balance: Balance) : Balance {
        return Balance(currency = currencyReceive, value = balance.value + sumReceive)
    }
    
    fun updateFeeRate(rate: Float) {
        feeRate = rate
        feeSum = if (feeRate != 0f) sumSell / feeRate else 0f
    }
}

