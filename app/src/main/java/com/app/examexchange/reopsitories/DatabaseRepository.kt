package com.app.examexchange.reopsitories

import androidx.annotation.WorkerThread
import com.app.examexchange.database.MyDatabase
import com.app.examexchange.database.entities.Currency
import kotlinx.coroutines.flow.Flow

class DatabaseRepository(database: MyDatabase) {
    private val currencyDao = database.currencyDao()
    
    var allCurrencies: Flow<List<Currency>> = currencyDao.allCurrencies()
    
    @WorkerThread
    suspend fun updateCurrencies(listCurrency: List<Currency>) {
        currencyDao.updateCurrencies(listCurrency)
    }
}