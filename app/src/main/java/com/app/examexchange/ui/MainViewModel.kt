package com.app.examexchange.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.app.examexchange.database.entities.Currency
import com.app.examexchange.reopsitories.DatabaseRepository

class MainViewModel(private val repository: DatabaseRepository) : ViewModel() {
    var allCurrencies: LiveData<List<Currency>> = repository.allCurrencies.asLiveData()
}