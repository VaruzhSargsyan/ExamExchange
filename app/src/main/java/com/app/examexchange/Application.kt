package com.app.examexchange

import android.app.Application
import com.app.examexchange.database.MyDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class Application : Application() {
    private val applicationScope by lazy { CoroutineScope(SupervisorJob() + Dispatchers.IO) }
    private val database by lazy { MyDatabase.getDatabase(this, applicationScope) }
}