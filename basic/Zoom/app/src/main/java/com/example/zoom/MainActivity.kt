package com.example.zoom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow.Companion.Clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    val scale = remember { mutableFloatStateOf(1f) }
    val offsetX = remember { mutableFloatStateOf(0f) }
    val offsetY = remember { mutableFloatStateOf(0f) }
    val maxZoom = 2f
    val minZoom = 0.5f
    val rotationState = remember { mutableFloatStateOf(1f) }



//    Box {
//        Image(painter = painterResource(id = tamtam)
//            , contentDescription = "탬탬버린"
//            , modifier = Modifier
//                .fillMaxSize()
//                .scale(scale.floatValue)
//                .offset(offsetX.floatValue.dp, offsetY.floatValue.dp)
//        )
//    }
//


//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//            .pointerInput(Unit) {
//                detectTransformGestures { _, pan, zoom, rotation ->
//                    scale.floatValue *= zoom
//                    rotationState.floatValue += rotation
//                    if (scale.floatValue > maxZoom) {
//                        scale.floatValue = maxZoom
//                    } else if(scale.floatValue < minZoom){
//                        scale.floatValue = minZoom
//                    }
//                    offsetX.floatValue += pan.x
//                    offsetY.floatValue += pan.y
//                }
//
//            }
//    ) {
//            Image(painter = painterResource(id = tamtam)
//                , contentDescription = "탬탬버린"
//                , modifier = Modifier
//                    .fillMaxSize()
//                    .scale(scale.floatValue)
//                    .offset(offsetX.floatValue.dp, offsetY.floatValue.dp)
//            )
//    }

    Box(
        modifier = Modifier
            .clip(RectangleShape) // Clip the box content
            .fillMaxSize() // Give the size you want...
            .pointerInput(Unit) {
                detectTransformGestures { centroid, pan, zoom, rotation ->
                    scale.value *= zoom
                    rotationState.value += rotation
                }
            }
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.Center) // keep the image centralized into the Box
                .graphicsLayer(
                    // adding some zoom limits (min 50%, max 200%)
                    scaleX = maxOf(.5f, minOf(3f, scale.value)),
                    scaleY = maxOf(.5f, minOf(3f, scale.value)),
                ),
            contentDescription = null,
            painter = painterResource(tamtam)
        )
    }

//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//            .pointerInput(Unit) {
//                detectTransformGestures { _, pan, zoom, _ ->
//                    scale.floatValue *= zoom
//                    if (scale.floatValue > maxZoom) {
//                        scale.floatValue = maxZoom
//                    } else if(scale.floatValue < minZoom){
//                        scale.floatValue = minZoom
//                    }
//                    offsetX.floatValue += pan.x
//                    offsetY.floatValue += pan.y
//                }
//
//            }
//    ) {
//            Image(painter = painterResource(id = tamtam)
//                , contentDescription = "탬탬버린"
//                , modifier = Modifier
//                    .fillMaxSize()
//                    .scale(scale.floatValue)
//                    .offset(offsetX.floatValue.dp, offsetY.floatValue.dp)
//            )
//    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ZoomTheme {
        Greeting()
    }
}