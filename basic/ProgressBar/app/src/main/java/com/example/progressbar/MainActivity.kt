package com.example.progressbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.progressbar.ui.theme.ProgressBarTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProgressBarTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(top = 30.dp),
            text = "Progrss 예제"
        )

        Spacer(modifier = Modifier.height(50.dp))

        ProgressBarExample()
    }
}

@Composable
fun ProgressBarExample(){
    // 백그라운드 작업 진행 여부를 나타내는 State
    var isTaskRunning by remember { mutableStateOf(false) }

    // ProgressBar의 현재 진행 상태를 나타내는 State
    var progress by remember { mutableStateOf(0.0f) }

    // 백그라운드 작업 스레드 시작 및 ProgressBar 업데이트
    LaunchedEffect(Unit) {
        isTaskRunning = true
        repeat(100) {
            // 예시로 100번 반복하며 작업 진행 상태 업데이트
            progress = (it + 1) / 100f
            delay(10) // 각 작업 사이의 딜레이
        }
        isTaskRunning = false
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        // 백그라운드 작업 진행 중이면 ProgressBar 표시
        if (isTaskRunning) {
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )

            Text(
                modifier = Modifier.padding(top = 20.dp),
                text = "${String.format("%.0f", progress * 100)}%"

            )
        } else {
            // 작업이 완료되면 완료 메시지 표시
            Text("작업이 완료되었습니다!", fontWeight = FontWeight.Bold)
        }
    }
}




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProgressBarTheme {
        Greeting()
    }

}

