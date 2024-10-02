package com.example.animationtext

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.animationtext.ui.theme.AnimationTextTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnimationTextTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "깜빡거리는 텍스트",
            color = Color.White.copy(alpha = blinkText())
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "슬라이드 효과",
            color = Color.White ,
            modifier = Modifier.offset(x = slideInOut())
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "텍스트 색 변경",
            color = colorChange()
        )


        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = textTyping(),
            color = Color.White
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "텍스트 회전",
            modifier = Modifier.rotate(rotateText()),
            color = Color.White
        )

    }
}

// 깜빡 거리는 효과
@Composable
fun blinkText() : Float{
    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    return alpha
}

// 슬라이드 효과
@Composable
fun slideInOut() : Dp {
    var visible by remember { mutableStateOf(true) }

    val offset by animateDpAsState(
        targetValue = if (visible) 0.dp else 250.dp,
        animationSpec = tween(durationMillis = 500)
    )


    LaunchedEffect(Unit) {
        while (true) {
            delay(800)
            visible = !visible
        }
    }

    return offset
}

// 색변경 효과
@Composable
fun colorChange() : Color {
    var isActive by remember { mutableStateOf(true) }

    val color by animateColorAsState(
        targetValue = if (isActive) Color.Red else Color.White,
        animationSpec = tween(durationMillis = 1000)
    )

    LaunchedEffect(Unit) {
        while (true) {
            delay(1500)
            isActive = !isActive
        }
    }

    return color
}

// 타이핑 효과
@Composable
fun textTyping() : String {
    var displayedText by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        val text = "타이핑 텍스트"
        val delayPerCharacter = 100L  // 각 문자에 대한 타이핑 속도
        val holdTimePerCharacter = 200L  // 문자 수에 따라 전체 텍스트가 유지되는 시간 비율

        while (true) {  // 무한 루프를 사용하여 반복
            text.forEachIndexed { index, _ ->
                delay(delayPerCharacter)
                displayedText = text.substring(0, index + 1)
            }
            delay(holdTimePerCharacter * text.length)  // 텍스트 길이에 비례하여 대기 시간 설정
            displayedText = ""  // 텍스트 초기화
        }

    }
    return displayedText
}

// 회전 효과
@Composable
fun rotateText() : Float {
    var rotating by remember { mutableStateOf(true) }

    val rotation by animateFloatAsState(
        targetValue = if (rotating) 360f else 0f,
        animationSpec = tween(durationMillis = 1000)
    )

    LaunchedEffect(Unit) {
        while (true) {
            delay(1500)
            rotating = !rotating
        }
    }

    return rotation

}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AnimationTextTheme {
        Greeting()
    }
}