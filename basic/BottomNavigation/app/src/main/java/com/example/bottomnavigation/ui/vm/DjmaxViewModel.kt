package com.example.bottomnavigation.ui.vm

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel



class DjmaxViewModel : ViewModel() {

    val buttonClicked = mutableStateOf(false)

    fun clickButton(){
        buttonClicked.value = !buttonClicked.value
    }
}