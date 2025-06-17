package com.example.naverocrtest.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val api: OcrApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://usob7xcot3-service-api.incizorlensapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OcrApiService::class.java)
    }
}