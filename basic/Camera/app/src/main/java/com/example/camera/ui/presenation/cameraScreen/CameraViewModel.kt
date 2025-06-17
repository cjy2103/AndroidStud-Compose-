package com.example.camera.ui.presenation.cameraScreen

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor() : ViewModel() {
    private val _bitmap = MutableStateFlow<Bitmap?>(null)
    val bitmap: StateFlow<Bitmap?> = _bitmap

    fun setBitmap(image: Bitmap) {
        _bitmap.value = image
    }
}