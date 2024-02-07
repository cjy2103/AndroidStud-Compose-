package com.example.lottie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.lottie.ui.theme.LottieTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LottieTheme {
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
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.box))

    val progress by animateLottieCompositionAsState(composition = composition
        , iterations = LottieConstants.IterateForever)

    Column(
        modifier = Modifier
            .padding(top = 50.dp)
            .fillMaxSize()
    ){

        LottieAnimation(
            modifier = Modifier.size(width = 200.dp, height = 200.dp)
                .align(Alignment.CenterHorizontally),
            composition = composition, progress = { progress }

        )

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LottieTheme {
        Greeting()
    }
}