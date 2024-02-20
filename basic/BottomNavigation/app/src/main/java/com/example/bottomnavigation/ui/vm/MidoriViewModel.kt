package com.example.bottomnavigation.ui.vm

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MidoriViewModel : ViewModel() {
    val buttonClicked = mutableStateOf(false)

    fun clickButton(){
        buttonClicked.value = !buttonClicked.value
    }
}