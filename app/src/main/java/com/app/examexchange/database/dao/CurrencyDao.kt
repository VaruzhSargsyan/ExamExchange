package com.app.examexchange.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.app.examexchange.database.entities.Currency
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {
    @Query("Select * from table_currencies order by name ASC")
    fun allCurrencies(): Flow<List<Currency>>
    
    @Upsert
    fun updateCurrencies(listCurrency: List<Currency>)
}