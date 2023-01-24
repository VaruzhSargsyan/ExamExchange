package com.app.examexchange.model

import androidx.lifecycle.ViewModel
import com.app.examexchange.api.Api
import com.app.examexchange.api.Client
import com.app.examexchange.application.ApplicationModel
import com.app.examexchange.reopsitories.DatabaseRepository

/*
 * This class is made with a hope to use for other View Models if they could be created
 */
abstract class AbstractViewModel(protected val applicationModel: ApplicationModel) : ViewModel() {
    protected val api: Api by lazy { Client.getClient().create(Api::class.java) }
}