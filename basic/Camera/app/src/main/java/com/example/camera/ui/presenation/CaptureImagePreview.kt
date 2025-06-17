package com.example.camera.ui.presenation

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment

@Composable
fun CapturedImagePreview(bitmap: Bitmap?) {
    bitmap?.let {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = "Captured Image",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}