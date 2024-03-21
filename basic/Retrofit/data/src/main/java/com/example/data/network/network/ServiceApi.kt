package com.example.data.network


import com.example.data.data.PostResult
import retrofit2.http.GET
import retrofit2.http.Path

interface ServiceApi {
    @GET("posts/{post}")
    suspend fun getPosts(@Path("post") post : String) : PostResult
}