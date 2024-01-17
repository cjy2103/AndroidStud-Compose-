package com.example.room.vm

import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.room.repository.DataBase
import com.example.room.repository.DataRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {


    val text = mutableStateOf("저장된 값 없음")

    private val repository : DataRepository by lazy {
        DataRepository(DataBase.getInstance(application))
    }

    fun saveData(word : String){
        // 나중에 Room에 저장하는걸로
//        text.value = word
        viewModelScope.launch {
            repository.insertData("데이터 저장")
        }
    }

    fun deleteData(word : String){
        val data = repository.getData("데이터 저장")
        viewModelScope.launch {
            if(data.first().word == "데이터 저장"){

            }
        }
    }

}