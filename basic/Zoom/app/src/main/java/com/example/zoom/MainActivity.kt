package com.example.zoom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.zoom.R.drawable.tamtam
import com.example.zoom.ui.theme.ZoomTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZoomTheme {
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

    var zoom by remember { mutableFloatStateOf(1f) }
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }
    val minScale = 0.5f
    val maxScale = 2f

    val imageWidth = painterResource(id = tamtam).intrinsicSize.width
    val imageHeight = painterResource(id = tamtam).intrinsicSize.height

    Box (
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTransformGestures(
                    onGesture = { _, pan, gestureZoom, _ ->
                        zoom = (zoom * gestureZoom).coerceIn(minScale, maxScale)
                        if (zoom > 1) {
                            val maxOffsetX = imageWidth + 50f
                            val maxOffsetY = imageHeight
                            offsetX += pan.x * zoom
                            offsetY += pan.y * zoom
                            offsetX = offsetX.coerceIn(-maxOffsetX, maxOffsetX)
                            offsetY = offsetY.coerceIn(-maxOffsetY, maxOffsetY)
                        } else {
                            offsetX = 0f
                            offsetY = 0f
                        }
                    }
                )
            }
    ){
        Image(
            painter = painterResource(id = tamtam),
            contentDescription = "탬탬버린",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .graphicsLayer(
                    scaleX = zoom,
                    scaleY = zoom,
                    translationX = offsetX,
                    translationY = offsetY,
                )
                .fillMaxSize()
        )
    }


    // BOX 쓰기 씷으면 이거 사용

//    Image(
//        painter = painterResource(id = tamtam),
//        contentDescription = "some description here",
//        contentScale = ContentScale.Fit,
//        modifier = Modifier
//            .graphicsLayer(
//                scaleX = zoom,
//                scaleY = zoom,
//                translationX = offsetX,
//                translationY = offsetY,
//            )
//            .pointerInput(Unit) {
//                detectTransformGestures(
//                    onGesture = { _, pan, gestureZoom, _ ->
//                        zoom = (zoom * gestureZoom).coerceIn(minScale, maxScale)
//                        if (zoom > 1) {
//                            val maxOffsetX = imageWidth + 50f
//                            val maxOffsetY = imageHeight
//                            offsetX += pan.x * zoom
//                            offsetY += pan.y * zoom
//                            offsetX = offsetX.coerceIn(-maxOffsetX, maxOffsetX)
//                            offsetY = offsetY.coerceIn(-maxOffsetY, maxOffsetY)
//                        } else {
//                            offsetX = 0f
//                            offsetY = 0f
//                        }
//                    }
//                )
//            }
//            .fillMaxSize()
//    )

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    ZoomTheme {
        Greeting()
    }
}