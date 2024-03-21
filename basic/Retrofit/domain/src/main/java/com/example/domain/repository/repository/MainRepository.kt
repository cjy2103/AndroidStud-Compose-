package com.example.domain.repository.repository

import com.example.data.data.PostResult
import com.example.data.network.RetrofitClient
import com.example.data.network.ServiceApi


class MainRepository   {
    private val service = RetrofitClient.getClient().create(ServiceApi::class.java)
    suspend fun callData(post : String) : PostResult{
        return try {
            service.getPosts(post)
        } catch (e: Exception) {
            throw e
        }
    }
}