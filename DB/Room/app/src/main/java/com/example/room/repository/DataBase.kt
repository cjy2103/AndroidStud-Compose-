package com.example.room.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Data::class], version = 1)
abstract class DataBase : RoomDatabase() {
    abstract fun dataDao() : DataDao

    companion object {
        private const val DATABASE_NAME = "data_db"

        @Volatile
        private var instance : DataBase? = null

        fun getInstance(context : Context) : DataBase{
            return instance ?: synchronized(this){
                instance ?: buildDataBase(context).also{
                    instance = it
                }
            }
        }

        private fun buildDataBase(context: Context): DataBase {
            return Room.databaseBuilder(context, DataBase::class.java, DATABASE_NAME)
                .build()
        }
    }
}