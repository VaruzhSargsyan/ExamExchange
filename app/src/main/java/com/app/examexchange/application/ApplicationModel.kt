package com.app.examexchange.application

import com.app.examexchange.database.MyDatabase
import com.app.examexchange.model.FeeCalculator
import com.app.examexchange.reopsitories.DatabaseRepository
import kotlinx.coroutines.CoroutineScope

class ApplicationModel(val application: Application, private val applicationScope: CoroutineScope) {

    private val database by lazy { MyDatabase.getDatabase(application, applicationScope) }
    val repository by lazy { DatabaseRepository(database) }
    val feeCalculator by lazy { FeeCalculator(database) }

}