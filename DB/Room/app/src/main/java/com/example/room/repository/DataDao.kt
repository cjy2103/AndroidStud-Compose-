package com.example.room.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface DataDao {
    @Query("SELECT * FROM Data WHERE word = :word")
    fun loadByData(word : String) : Flow<Data>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(data : Data)

    @Delete
    suspend fun deleteData(data: Data)
}