package com.example.room.repository

import kotlinx.coroutines.flow.Flow

class DataRepository(private val database : DataBase) {
    fun getData(word: String) : Flow<Data> {
        return database.dataDao().loadByData(word)
    }

    suspend fun insertData(word : String) {
        val data = Data(word)
        database.dataDao().insertData(data)
    }
}