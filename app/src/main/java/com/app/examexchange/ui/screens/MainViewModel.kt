package com.app.examexchange.ui.screens

import androidx.lifecycle.*
import com.app.examexchange.application.ApplicationModel
import com.app.examexchange.database.entities.*
import com.app.examexchange.model.AbstractViewModel
import com.app.examexchange.utils.EventManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(applicationModel: ApplicationModel) : AbstractViewModel(applicationModel) {
    
    var allCurrencies: LiveData<List<Currency>> = applicationModel.repository.allCurrencies.asLiveData()
    var listBalance: LiveData<List<Balance>> = applicationModel.repository.listBalance.asLiveData()
    var currencySell: MutableLiveData<Currency?> = MutableLiveData(null)
    var currencyReceive: MutableLiveData<Currency?> = MutableLiveData(null)
    var sumSell: MutableLiveData<Float> = MutableLiveData(0f)
    var sumReceive: MutableLiveData<Float> = MutableLiveData(0f)
//    var exchange: Exchange = Converter.convertFromTo(getCurrency("EUR"), getCurrency("USD"), 0f)
    
    private val eventManager = EventManager()
    
    init {
        downloadCurrencies()
        // call download method on every 5 second
        eventManager.setup(5) { downloadCurrencies() }
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
    
    fun updateSellCurrency(currency: String?) {
        if (currencySell.value?.name != currency) {
            getCurrency(currency)?.let {
                currencySell.postValue(it)
                sumReceive.postValue(currencyReceive.value!!.rate / it.rate * sumSell.value!!)
            }
        }
    }
    
    fun updateReceiveCurrency(currency: String?) {
        if (currencyReceive.value?.name != currency) {
            getCurrency(currency)?.let {
                currencyReceive.postValue(it)
                sumReceive.postValue(it.rate / currencySell.value!!.rate * sumSell.value!!)
            }
        }
    }
    
    fun updateSellSum(sum: Float) {
        if (sumSell.value != sum) {
            sumSell.postValue(sum)
            sumReceive.postValue(currencyReceive.value!!.rate / currencySell.value!!.rate * sum)
        }
    }
    
    fun updateReceiveSum(sum: Float) {
        if (sumReceive.value != sum) {
            sumReceive.postValue(sum)
            sumSell.postValue(currencySell.value!!.rate / currencyReceive.value!!.rate * sum)
        }
    }
    
    private fun getCurrency(currency: String?) : Currency? = allCurrencies.value?.find { it.name == currency }
//    fun initDefaultExchange() {
//        exchange.postValue(Converter.convertFromTo(getCurrency("EUR"), getCurrency("USD"), 0f))
//    }
    
    fun initDefaultExchange() {
        if (currencySell.value == null) {
            currencySell.postValue(getCurrency("EUR"))
        }
        currencyReceive.value?: run { currencyReceive.postValue(getCurrency("USD")) }
    }
}

