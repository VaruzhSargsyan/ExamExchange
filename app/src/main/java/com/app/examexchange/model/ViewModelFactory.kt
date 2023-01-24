package com.app.examexchange.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.examexchange.application.ApplicationModel
import com.app.examexchange.ui.screens.MainViewModel

/*
 * View Model Factory
 */
class ViewModelFactory(private val applicationModel: ApplicationModel) : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>) : T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java))
            return MainViewModel(applicationModel) as T
        
        throw IllegalArgumentException("Unknown Class For View Model")
    }
}