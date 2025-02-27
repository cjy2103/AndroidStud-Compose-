package com.example.myapplication.data.repository

import com.example.myapplication.common.ApiResult
import com.example.myapplication.data.api.ApiService
import com.example.myapplication.data.model.TestResponse
import javax.inject.Inject

class TestRepository @Inject constructor(
    private val apiService: ApiService  // ✅ Hilt가 NetworkModule을 통해 자동 주입
) {

    suspend fun fetchTestList(): ApiResult<List<TestResponse>> {
        return try {
            val response = apiService.getTestList()
            if (response.isSuccessful) {
                ApiResult.Success(response.body() ?: emptyList())
            } else {
                ApiResult.Failure(Exception("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            ApiResult.Failure(e)
        }
    }
}