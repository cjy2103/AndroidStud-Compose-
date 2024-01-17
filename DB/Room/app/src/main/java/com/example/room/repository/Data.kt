package com.example.room.repository

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Data")
data class Data(
    @PrimaryKey
    val word : String
)
