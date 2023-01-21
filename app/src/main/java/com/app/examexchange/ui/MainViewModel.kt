package com.app.examexchange.ui

import androidx.lifecycle.*
import com.app.examexchange.api.Api
import com.app.examexchange.api.Client
import com.app.examexchange.database.entities.Currencies
import com.app.examexchange.database.entities.Currency
import com.app.examexchange.reopsitories.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: DatabaseRepository) : ViewModel() {
    
    val api: Api by lazy { Client.getClient().create(Api::class.java) }
    
    var allCurrencies: LiveData<List<Currency>> = repository.allCurrencies.asLiveData()
    
    fun downloadCurrencies() {
        viewModelScope.launch(Dispatchers.IO) {
            val response: Response<Currencies> = api.downloadAllTags()
            if (response.isSuccessful)
                response.body()?.let { currencies ->
                    repository.updateCurrencies(currencies.getCurrencies())
                }
        }
    }
}

class ModelFactory(private val repository: DatabaseRepository) : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>) : T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java))
            return MainViewModel(repository) as T
        
        throw IllegalArgumentException("Unknown Class For View Model")
    }
}