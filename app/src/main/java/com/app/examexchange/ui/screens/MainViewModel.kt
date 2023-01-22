package com.app.examexchange.ui.screens

import androidx.lifecycle.*
import com.app.examexchange.application.ApplicationModel
import com.app.examexchange.database.entities.Balance
import com.app.examexchange.database.entities.Currencies
import com.app.examexchange.database.entities.Currency
import com.app.examexchange.model.AbstractViewModel
import com.app.examexchange.utils.EventManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(applicationModel: ApplicationModel) : AbstractViewModel(applicationModel) {
    
    var allCurrencies: LiveData<List<Currency>> = applicationModel.repository.allCurrencies.asLiveData()
    var listBalance: LiveData<List<Balance>> = applicationModel.repository.listBalance.asLiveData()
    
    private val eventManager = EventManager()
    
    init {
        downloadCurrencies()
        // call download method on every 5 second
        eventManager.setup { downloadCurrencies() }
    }
    
    private fun downloadCurrencies() {
        viewModelScope.launch(Dispatchers.IO) {
            val response: Response<Currencies> = api.downloadAllTags()
            if (response.isSuccessful)
                response.body()?.let { currencies ->
                    applicationModel.repository.updateCurrencies(currencies.getCurrencies())
                }
            //
            eventManager.prepareNextCall()
        }
    }
    
    override fun onCleared() {
        super.onCleared()
        
        eventManager.stop()
    }
}

