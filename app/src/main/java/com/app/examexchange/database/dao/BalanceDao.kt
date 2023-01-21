package com.app.examexchange.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.app.examexchange.database.entities.Balance

@Dao
interface BalanceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(balance: Balance)
    
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(balance: Balance)
}