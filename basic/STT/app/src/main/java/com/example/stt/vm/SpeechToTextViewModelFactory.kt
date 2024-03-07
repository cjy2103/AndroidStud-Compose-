package com.example.stt.vm

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SpeechToTextViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SpeechToTextViewModel::class.java)) {
            return SpeechToTextViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}