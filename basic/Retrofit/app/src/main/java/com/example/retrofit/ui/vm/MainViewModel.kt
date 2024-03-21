package com.example.retrofit.ui.vm

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.network.network.RetrofitCallback
import com.example.domain.repository.MainRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val mainRepository = MainRepository()

    val data = mutableStateOf("데이터 들어오는 부분")

    fun dataLoad(){
        viewModelScope.launch {
            mainRepository.callData(object : RetrofitCallback{
                override fun onSuccesses(result: String) {
                    data.value = result
                }

                override fun onError(throwable: Throwable) {
                    data.value = "오류발생 $throwable"
                }

            })
        }
    }
}