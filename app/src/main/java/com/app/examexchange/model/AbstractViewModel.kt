package com.app.examexchange.model

import androidx.lifecycle.ViewModel
import com.app.examexchange.api.Api
import com.app.examexchange.api.Client
import com.app.examexchange.application.ApplicationModel
import com.app.examexchange.reopsitories.DatabaseRepository

abstract class AbstractViewModel(protected val applicationModel: ApplicationModel) : ViewModel() {
    protected val api: Api by lazy { Client.getClient().create(Api::class.java) }
    
//    private val repository: DatabaseRepository by lazy { applicationModel.repository }
}