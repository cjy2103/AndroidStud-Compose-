package com.example.myapplication

import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.OptIn
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    VideoPlayerScreen(
                        modifier = Modifier
                            .fillMaxSize() // 화면 전체 크기 채우기
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}
@RequiresApi(Build.VERSION_CODES.R)
@OptIn(UnstableApi::class)
@Composable
fun VideoPlayerScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val activity = SystemUtil.findActivity(LocalContext.current)
    val window = activity.window
    SystemUtil.softNavigationBarHide(window)
    SystemUtil.statusSetting(window)

    // raw 리소스 파일 URI 생성
    val videoUri = Uri.parse("android.resource://${context.packageName}/raw/sample")

    // ExoPlayer 인스턴스 생성
    val exoPlayer = ExoPlayer.Builder(context).build().apply {
        setMediaItem(MediaItem.fromUri(videoUri))
        repeatMode = ExoPlayer.REPEAT_MODE_ALL // 무한 반복 설정
        prepare() // 준비
        playWhenReady = true // 자동 재생
    }

    // ExoPlayer 해제 처리
    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    // PlayerView 포함
    AndroidView(
        modifier = modifier.fillMaxSize(), // PlayerView를 화면 전체로 설정
        factory = { context ->
            PlayerView(context).apply {
                player = exoPlayer
                useController = false // 터치 시 UI 표시 비활성화
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM // 크기 조정 모드 설정
            }
        }
    )
}