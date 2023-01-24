package com.app.examexchange.ui.screens

import androidx.lifecycle.*
import com.app.examexchange.R
import com.app.examexchange.application.ApplicationModel
import com.app.examexchange.database.entities.*
import com.app.examexchange.model.AbstractViewModel
import com.app.examexchange.utils.EventManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

/*
 * View Model to support the activity/UI
 */
class MainViewModel(applicationModel: ApplicationModel) : AbstractViewModel(applicationModel) {
    
    var listCurrencies: LiveData<List<Currency>> = applicationModel.repository.allCurrencies.asLiveData()
    var listBalance: LiveData<List<Balance>> = applicationModel.repository.listBalance.asLiveData()
    var currencySell: MutableLiveData<Currency?> = MutableLiveData(null)
    var currencyReceive: MutableLiveData<Currency?> = MutableLiveData(null)
    var sumSell: MutableLiveData<Float> = MutableLiveData(0f)
    var sumReceive: MutableLiveData<Float> = MutableLiveData(0f)
    var message: MutableLiveData<Pair<String, String>?> = MutableLiveData(null)
    
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
                val rateReceive = currencyReceive.value?.rate?:0f
                sumReceive.postValue(rateReceive / it.rate * sumSell.value!!)
            }
        }
    }
    
    fun updateReceiveCurrency(currency: String?) {
        if (currencyReceive.value?.name != currency) {
            getCurrency(currency)?.let {
                currencyReceive.postValue(it)
                val rateSell = currencySell.value?.rate ?: 0f
                if (rateSell == 0f)
                    sumReceive.postValue(0f)
                else
                    sumReceive.postValue(it.rate / rateSell * sumSell.value!!)
            }
        }
    }
    
    fun updateSellSum(sum: Float) {
        if (sumSell.value != sum) {
            sumSell.postValue(sum)
            val rateSell = currencySell.value?.rate ?: 0f
            val rateReceive = currencyReceive.value?.rate ?: 0f
            if (rateSell == 0f || rateReceive == 0f)
                sumReceive.postValue(0f)
            else
                sumReceive.postValue(currencyReceive.value!!.rate / currencySell.value!!.rate * sum)
        }
    }
    
    fun updateReceiveSum(sum: Float) {
        if (sumReceive.value != sum) {
            sumReceive.postValue(sum)
            val rateSell = currencySell.value?.rate ?: 0f
            val rateReceive = currencyReceive.value?.rate ?: 0f
            if (rateSell == 0f || rateReceive == 0f)
                sumSell.postValue(0f)
            else
                sumSell.postValue(currencySell.value!!.rate / currencyReceive.value!!.rate * sum)
        }
    }
    
    private fun getCurrency(currency: String?) : Currency? = listCurrencies.value?.find { it.name == currency }
    private fun getBalance(currency: String?) : Balance? = listBalance.value?.find { it.currency == currency }
    
    fun initDefaultExchange() {
        currencySell.value?: run { currencySell.postValue(getCurrency("EUR")) }
        currencyReceive.value?: run { currencyReceive.postValue(getCurrency("USD")) }
    }
    
    fun verifyAndSubmitExchange() {
        viewModelScope.launch(Dispatchers.IO) {
            if (currencySell.value?.name == currencyReceive.value?.name) {
                val title = applicationModel.application.getString(R.string.title_currency_did_not_converted)
                val body = applicationModel.application.getString(R.string.text_both_currencies_are_the_same)
                message.postValue(Pair(title,body))
                return@launch
            }
            if (sumSell.value!! <= 0f || sumReceive.value!! <= 0f) {
                val title = applicationModel.application.getString(R.string.title_currency_did_not_converted)
                val body = applicationModel.application.getString(R.string.text_please_enter_positive_values)
                message.postValue(Pair(title,body))
                return@launch
            }
            val exchange =
                Exchange(
                    currencySell.value!!, currencyReceive.value!!,
                    sumSell.value!!, sumReceive.value!!, 0f, 0f
                )
            
            applicationModel.feeCalculator.updateFee(exchange)
            
            val balanceSell = getBalance(currencySell.value?.name)
            val balanceReceive = getBalance(currencyReceive.value?.name)
    
            val balanceUpdatedSell = exchange.updateSellBalance(balanceSell ?: Balance(currencySell.value!!.name, 0f))
            val balanceUpdatedReceive = exchange.updateReceiveBalance(balanceReceive ?: Balance(currencyReceive.value!!.name, 0f))
            
            if (balanceUpdatedSell.value < 0) {
                val title = applicationModel.application.getString(R.string.title_currency_did_not_converted)
                val body =
                    String.format(
                        applicationModel.application.getString(R.string.text_not_enough_funds),
                        balanceSell?.currency ?: ""
                    )
                message.postValue(Pair(title,body))
                return@launch
            }
    
            applicationModel.repository.upsert(listOf(balanceUpdatedSell, balanceUpdatedReceive))
            applicationModel.repository.insert(exchange)
    
            val title = applicationModel.application.getString(R.string.title_currency_converted)
            val body =
                String.format(
                    applicationModel.application.getString(R.string.text_currency_converted_formatted),
                    sumSell.value,
                    currencySell.value?.name,
                    sumReceive.value,
                    currencyReceive.value?.name,
                    exchange.feeSum,
                    balanceUpdatedSell.currency,
                )
            message.postValue(Pair(title,body))
    
            sumSell.postValue(0f)
            sumReceive.postValue(0f)
        }
    }
}

