package com.example.launcher.ui.presentation

import android.net.Uri
import androidx.annotation.OptIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext

import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.ImageDecoderDecoder
import com.example.launcher.R
import com.example.launcher.SystemUtil

@Composable
fun VideoScreen() {
    val context = LocalContext.current
    val activity = SystemUtil.findActivity(context)
    val window = activity.window
    SystemUtil.softNavigationBarHide(window)
    SystemUtil.statusSetting(window)

    // WebP 애니메이션을 Coil로 표시
    Image(
        painter = rememberAsyncImagePainter(
            model = R.drawable.speaking, // WebP 파일 리소스 ID
            imageLoader = ImageLoader.Builder(context)
                .components {
                    add(ImageDecoderDecoder.Factory()) // 애니메이션 WebP 지원
                }
                .build()
        ),
        contentDescription = "WebP Animation",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}