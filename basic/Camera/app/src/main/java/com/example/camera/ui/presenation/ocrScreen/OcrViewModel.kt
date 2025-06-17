package com.example.camera.ui.presenation.ocrScreen

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.naverocrtest.data.model.OcrResult
import com.example.naverocrtest.data.repository.OcrRepository
import com.google.gson.Gson
import java.io.File

class OcrViewModel : ViewModel() {

    private val repository = OcrRepository()

    private val _ocrResult = MutableLiveData<OcrResult?>()
    val ocrResult: LiveData<OcrResult?> = _ocrResult

    fun processImageFile(file: File) {
        repository.extractOcr(
            image = file,
            onResult = { result ->
                _ocrResult.postValue(result)
                Log.d("OCR_DEBUG", Gson().toJson(result))
            },
            onError = { error ->
                Log.e("OCR", "Error: ${error.message}")
            }
        )
    }

    fun processImage(context: Context, uri: Uri) {
        try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return
            val tempFile = File.createTempFile("ocr_", ".jpg", context.cacheDir)
            tempFile.outputStream().use { output ->
                inputStream.copyTo(output)
            }
            processImageFile(tempFile)
        } catch (e: Exception) {
            Log.e("OCR", "URI 처리 중 오류: ${e.message}")
        }
    }
}