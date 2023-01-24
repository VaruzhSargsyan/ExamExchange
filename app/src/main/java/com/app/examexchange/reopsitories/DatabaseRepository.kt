package com.app.examexchange.reopsitories

import androidx.annotation.WorkerThread
import androidx.sqlite.db.SimpleSQLiteQuery
import com.app.examexchange.database.MyDatabase
import com.app.examexchange.database.entities.Balance
import com.app.examexchange.database.entities.Currency
import com.app.examexchange.database.entities.Exchange
import kotlinx.coroutines.flow.Flow

class DatabaseRepository(database: MyDatabase) {
    private val currencyDao = database.currencyDao()
    private val balanceDao = database.balanceDao()
    private val exchangeDao = database.exchangeDao()
    
    var allCurrencies: Flow<List<Currency>> = currencyDao.allCurrencies()
    var listBalance: Flow<List<Balance>> = balanceDao.getBalance()
    
    @WorkerThread
    suspend fun updateCurrencies(listCurrency: List<Currency>) {
        currencyDao.updateCurrencies(listCurrency)
    }
    
    @WorkerThread
    suspend fun upsert(listBalance: List<Balance>) {
        balanceDao.upsert(listBalance)
    }
    
    @WorkerThread
    suspend fun insert(exchange: Exchange) {
        exchangeDao.insert(exchange)
    }
    
}