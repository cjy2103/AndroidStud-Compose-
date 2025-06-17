package com.example.camera.util

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import java.io.ByteArrayOutputStream

fun saveRotatedImage(
    context: Context,
    bitmap: Bitmap,
    onSaved: (Uri?) -> Unit
) {
    val rotatedBytes = ByteArrayOutputStream().use { stream ->
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        stream.toByteArray()
    }

    val filename = "IMG_${System.currentTimeMillis()}.jpg"
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, filename)
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/OCRTest")
    }

    val resolver = context.contentResolver
    val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

    uri?.let {
        resolver.openOutputStream(it)?.use { outputStream ->
            outputStream.write(rotatedBytes)
            outputStream.flush()
        }
    }

    onSaved(uri)
}