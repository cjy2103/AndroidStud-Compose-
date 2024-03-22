package com.example.domain.repository

import com.example.data.data.Data
import com.example.data.network.RetrofitClient
import com.example.data.network.ServiceApi

class MainRepository {
    private val service = RetrofitClient.getClient().create(ServiceApi::class.java)
    suspend fun callData() : Data{
        return try {
            service.getPosts()
        } catch (e: Exception) {
            throw e
        }
    }
}