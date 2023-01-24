package com.app.examexchange.database.dao

import androidx.room.*
import com.app.examexchange.database.entities.Balance
import com.app.examexchange.database.entities.Currency
import kotlinx.coroutines.flow.Flow

@Dao
interface BalanceDao {
    @Query("SELECT * FROM table_balance ORDER BY value ASC")
    fun getBalance(): Flow<List<Balance>>
    
    @Upsert
    suspend fun upsert(listBalance: List<Balance>)
}