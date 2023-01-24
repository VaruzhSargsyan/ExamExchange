package com.app.examexchange.api

import com.app.examexchange.database.entities.Currencies
import retrofit2.Response
import retrofit2.http.GET

interface Api {
    
    @GET("tasks/api/currency-exchange-rates")
    suspend fun downloadAllTags(): Response<Currencies>

}