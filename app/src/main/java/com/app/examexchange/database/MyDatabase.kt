package com.app.examexchange.database

import android.content.Context
import androidx.room.*
import androidx.room.Database
import androidx.sqlite.db.SupportSQLiteDatabase
import com.app.examexchange.database.dao.BalanceDao
import com.app.examexchange.database.dao.CurrencyDao
import com.app.examexchange.database.dao.ExchangeDao
import com.app.examexchange.database.dao.RuleDao
import com.app.examexchange.database.entities.Balance
import com.app.examexchange.database.entities.Currency
import com.app.examexchange.database.entities.Exchange
import com.app.examexchange.database.entities.Rule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Currency::class, Balance::class, Rule::class, Exchange::class], version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {
    
    abstract fun balanceDao() : BalanceDao
    abstract fun currencyDao() : CurrencyDao
    abstract fun exchangeDao() : ExchangeDao
    abstract fun ruleDao() : RuleDao
    
    private class DatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {

                }
            }
        }
    }
    
    companion object {
        @Volatile
        private var INSTANCE: MyDatabase? = null
        
        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): MyDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java,
                    "currency_exchange_database"
                )
                    .addCallback(DatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
    
}