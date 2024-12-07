package com.example.btexample.ui.presentation.testScreen

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor() : ViewModel() {

    // 텍스트 상태 관리
    private val _textState = mutableStateOf("")
    val textState: State<String> get() = _textState

    // 텍스트 변경 함수
    fun onTextChanged(newText: String) {
        _textState.value = newText
    }

}