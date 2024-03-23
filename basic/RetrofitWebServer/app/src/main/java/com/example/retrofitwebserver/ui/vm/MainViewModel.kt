package com.example.retrofitwebserver.ui.vm

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.DataRepository
import com.example.domain.repository.DataGetUseCase
import kotlinx.coroutines.launch

class MainViewModel : ViewModel(){
    private val dataGetUseCase = DataGetUseCase(DataRepository())

    val data = mutableStateOf("데이터 들어오는 부분")


    fun fetchData() {
        viewModelScope.launch {
            val result = dataGetUseCase.fetchData()
            data.value = result
        }
    }
}

