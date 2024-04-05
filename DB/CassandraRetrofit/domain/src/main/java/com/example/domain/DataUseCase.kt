package com.example.domain

import com.example.data.repository.DataRepository

class DataUseCase(private val dataRepository: DataRepository) {
    suspend fun fetchData() : String {
        return try{
            val result = dataRepository.callData()
            result.toString()
        } catch (e : Exception){
            "오류발생 $e"
        }
    }

}