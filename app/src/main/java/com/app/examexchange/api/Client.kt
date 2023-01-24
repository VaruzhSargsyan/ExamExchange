package com.app.examexchange.api

import com.google.gson.GsonBuilder
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Client {
    companion object {
        private var retrofit: Retrofit? = null
        private const val server = "https://developers.paysera.com/"
    
        private const val REQUEST_TIMEOUT = 15000
    
        fun getClient(): Retrofit {
            initOkHttp()
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(server)
                    .client(initOkHttp())
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
                    .build()
            }
            return retrofit!!
        }
    
        private fun initOkHttp(): OkHttpClient {
            val okHttpClient: OkHttpClient
            val httpClient: OkHttpClient.Builder = OkHttpClient().newBuilder()
                .connectTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
            val interceptor = Interceptor { chain: Interceptor.Chain ->
                var request = chain.request()
                var requestBuilder = request.headers.newBuilder()
                requestBuilder = requestBuilder
                    .add("Accept", "application/json")
                    .add("Content-Type", "application/json")
//                requestBuilder.add(
//                    "Authorization",
//                    "Bearer " + token
//                )
                val headers: Headers = requestBuilder.build()
                request = request.newBuilder().headers(headers).build()
                chain.proceed(request)
            }
            httpClient.addInterceptor(interceptor)
            okHttpClient = httpClient.build()
            return okHttpClient
        }
    }
}