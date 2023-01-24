package com.app.examexchange.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.app.examexchange.database.entities.Exchange

@Dao
interface ExchangeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exchange: Exchange)
    
    @RawQuery
    suspend fun runRuleOnHistory(query: SupportSQLiteQuery): Boolean?
    
}