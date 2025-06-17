package com.example.camera.util

import android.content.Context
import android.util.Base64
import android.util.Log
import java.io.File

fun encodeImageToBase64(file: File): String {
    val bytes = file.readBytes()
    return Base64.encodeToString(bytes, Base64.NO_WRAP)
}

fun clearOcrCacheFiles(context: Context) {
    val cacheDir = context.cacheDir
    cacheDir.listFiles()?.forEach { file ->
        if (file.name.startsWith("ocr_")) {
            val deleted = file.delete()
            Log.d("CACHE_CLEAR", "삭제됨: ${file.name} → $deleted")
        }
    }
}