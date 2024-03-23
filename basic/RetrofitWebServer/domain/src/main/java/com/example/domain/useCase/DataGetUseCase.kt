package com.example.domain.repository

import com.example.data.repository.DataRepository

class DataGetUseCase(private val mainRepository: DataRepository) {

    suspend fun fetchData(): String {
        return try {
            val result = mainRepository.callData()
            result.toString()
        } catch (e: Exception) {
            "오류발생 $e"
        }
    }
}