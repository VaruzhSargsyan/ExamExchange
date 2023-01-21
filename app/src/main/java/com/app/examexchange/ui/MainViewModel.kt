package com.app.examexchange.ui

import androidx.lifecycle.*
import com.app.examexchange.application.ApplicationModel
import com.app.examexchange.database.entities.Currencies
import com.app.examexchange.database.entities.Currency
import com.app.examexchange.model.AbstractViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(applicationModel: ApplicationModel) : AbstractViewModel(applicationModel) {
    
    var allCurrencies: LiveData<List<Currency>> = applicationModel.repository.allCurrencies.asLiveData()
    
    fun downloadCurrencies() {
        viewModelScope.launch(Dispatchers.IO) {
            val response: Response<Currencies> = api.downloadAllTags()
            if (response.isSuccessful)
                response.body()?.let { currencies ->
                    applicationModel.repository.updateCurrencies(currencies.getCurrencies())
                }
        }
    }
}

