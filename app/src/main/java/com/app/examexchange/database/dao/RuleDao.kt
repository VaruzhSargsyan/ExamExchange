package com.app.examexchange.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.examexchange.database.entities.Balance
import com.app.examexchange.database.entities.Rule
import kotlinx.coroutines.flow.Flow

@Dao
interface RuleDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(rules: List<Rule>)
    
    @Query("SELECT * FROM table_rules")
    fun getAllRules(): List<Rule>
    
}