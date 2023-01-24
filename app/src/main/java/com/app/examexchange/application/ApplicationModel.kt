package com.app.examexchange.application

import com.app.examexchange.database.MyDatabase
import com.app.examexchange.model.FeeCalculator
import com.app.examexchange.reopsitories.DatabaseRepository
import kotlinx.coroutines.CoroutineScope

/*
 * ApplicationModel is an additional layer to help in management of all layers like data repositories
 * This object should be single one during all lifecycle of the application and it is created in
 * Application class and will be available to all of View Models via Abstract View Model
 * Simple usage: instead of passing different layers/repos to ViewModelFactory object, the ApplicationModel
 * object goes there and provides repos/data to each ViewModel as keeper of all data layers
 */
class ApplicationModel(val application: Application, private val applicationScope: CoroutineScope) {

    private val database by lazy { MyDatabase.getDatabase(application, applicationScope) }
    val repository by lazy { DatabaseRepository(database) }
    val feeCalculator by lazy { FeeCalculator(database) }

}