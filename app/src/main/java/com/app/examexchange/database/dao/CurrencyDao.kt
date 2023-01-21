package com.app.examexchange.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.app.examexchange.database.entities.Currency
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {
    @Query("SELECT * FROM table_currencies ORDER BY name ASC")
    fun allCurrencies(): Flow<List<Currency>>
    
    @Upsert
    fun updateCurrencies(listCurrency: List<Currency>)
}