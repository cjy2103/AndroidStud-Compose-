package com.example.room.vm

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val text = mutableStateOf("저장된 값 없음")

    private val

    fun saveData(word : String){
        // 나중에 Room에 저장하는걸로
        text.value = word
    }

    fun deleteData(){
        text.value = "저장된 값 없음"
    }

}