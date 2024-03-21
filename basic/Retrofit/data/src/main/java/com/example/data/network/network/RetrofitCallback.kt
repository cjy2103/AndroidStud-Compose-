package com.example.data.network.network

interface RetrofitCallback {
    fun onSuccesses(result : String)
    fun onError(throwable: Throwable)
}