package com.app.examexchange.database.entities

import kotlin.streams.toList

data class Currencies(
    val base: String,
    val date: String,
    val rates: Map<String, Float>) {
    
    fun getCurrencies(): List<Currency> {
        val listCurrency = mutableListOf<Currency>()
        rates.entries.forEach { entry -> listCurrency.add(Currency(entry.key, entry.value)) }
        return listCurrency
    }
    
}
