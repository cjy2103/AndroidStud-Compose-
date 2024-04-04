package com.example.data.network

import com.example.data.data.Data
import retrofit2.http.GET

interface ServiceApi {
    @GET("test")
    suspend fun getPosts() : Data
}