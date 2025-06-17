package com.example.camera.ui.presenation.preivewScreen

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.camera.navigation.ScreenNavItem
import com.example.camera.ui.presenation.cameraScreen.CameraViewModel
import java.io.File

@Composable
fun PreviewScreen(navController: NavController) {
    val uri = navController.currentBackStackEntry
        ?.arguments
        ?.getString("uri")
        ?.let { Uri.parse(it) }

    val context = LocalContext.current
    val bitmap = remember(uri) {
        uri?.let {
            context.contentResolver.openInputStream(it)?.use { stream ->
                BitmapFactory.decodeStream(stream)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        bitmap?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = "Captured Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
        } ?: Text("이미지를 불러올 수 없습니다.")

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = {
                    uri?.path?.let { File(it).delete() }  // 🔥 임시 파일 삭제
                    navController.popBackStack()         // 🔁 다시 촬영
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("다시 촬영하기")
            }

            Button(
                onClick = {
                    uri?.let {
                        navController.navigate(ScreenNavItem.OcrResult.createRoute(it.toString()))
                    }
                },

                modifier = Modifier.weight(1f)
            ) {
                Text("OCR 추출하기")
            }
        }
    }
}