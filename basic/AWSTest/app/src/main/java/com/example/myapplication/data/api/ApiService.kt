package com.example.myapplication.data.api

import com.example.myapplication.data.model.TestResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/test")
    suspend fun getTestList() : Response<List<TestResponse>>
}