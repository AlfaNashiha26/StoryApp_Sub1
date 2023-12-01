package com.alfa.alfastoryapp.api

import com.alfa.alfastoryapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ApiConfig {
    fun getApiService(): ApiService {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor).connectTimeout(10, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS).build()
        val retrofit = Retrofit.Builder().baseUrl(BuildConfig.API_SERVER)
            .addConverterFactory(GsonConverterFactory.create()).client(client).build()
        return retrofit.create(ApiService::class.java)
    }
}