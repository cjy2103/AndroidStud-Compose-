package com.example.btexample.ui.presentation.testScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor() : ViewModel() {

    private val _pairDevice = mutableStateOf("")
    val pairDevice : State<String> = _pairDevice

    private val _textState = mutableStateOf("")
    val textState : State<String> = _textState

    fun onTextChanged(newText : String) {
        _textState.value = newText
    }

    fun onPairChanged(newDevice : String){
        _pairDevice.value = newDevice
    }
}