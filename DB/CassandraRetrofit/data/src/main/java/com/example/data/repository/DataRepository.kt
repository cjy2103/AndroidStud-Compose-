package com.example.data.repository

import com.example.data.data.Data
import com.example.data.network.RetrofitClient
import com.example.data.network.ServiceApi

class DataRepository {
    private val service = RetrofitClient.getClient().create(ServiceApi::class.java)

    suspend fun callData() : Data {
        return try {
            service.getPosts()
        } catch (e : Exception){
            throw e
        }
    }
}