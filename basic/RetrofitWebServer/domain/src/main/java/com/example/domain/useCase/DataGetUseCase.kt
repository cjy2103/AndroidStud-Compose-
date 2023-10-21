package com.example.domain.repository

import com.example.data.repository.MainRepository

class DataGetUseCase(private val mainRepository: MainRepository) {

    suspend fun fetchData(): String {
        return try {
            val result = mainRepository.callData()
            result.toString()
        } catch (e: Exception) {
            "오류발생 $e"
        }
    }
}