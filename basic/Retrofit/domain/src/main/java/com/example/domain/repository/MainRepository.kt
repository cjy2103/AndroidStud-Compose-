package com.example.domain.repository

import com.example.data.network.network.RetrofitCallback
import com.example.data.network.RetrofitClient
import com.example.data.network.ServiceApi


class MainRepository {
    private val service = RetrofitClient.getClient().create(ServiceApi::class.java)
    suspend fun callData(callback: RetrofitCallback){
        try {
            val result = service.getPosts("1")
            callback.onSuccesses(result.toString())
        } catch (e : Exception){
            callback.onError(e)
        }
    }
}