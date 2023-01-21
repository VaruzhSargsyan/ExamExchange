package com.app.examexchange.application

import android.app.Application
import com.app.examexchange.database.MyDatabase
import com.app.examexchange.reopsitories.DatabaseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class Application : Application() {
    private val applicationScope by lazy { CoroutineScope(SupervisorJob() + Dispatchers.IO) }
    val applicationModel: ApplicationModel by lazy { ApplicationModel(this@Application, applicationScope) }
}