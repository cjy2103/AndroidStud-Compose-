package com.example.retrofit.ui.vm

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.repository.MainRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val mainRepository = MainRepository()

    val data = mutableStateOf("데이터 들어오는 부분")

    fun dataLoad(){
        viewModelScope.launch {
            try {
                val result = mainRepository.callData("1")
                data.value = result.toString()
            } catch (e: Exception){
                data.value = "오류발생 $e"
            }
        }
    }
}