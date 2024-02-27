package com.example.outlinetext

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.outlinetext.ui.theme.OutLineTextTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OutLineTextTheme {
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
fun Greeting(modifier: Modifier = Modifier) {

    val text = "Text OutLine"

    Text(
        text = text,
        modifier = modifier ,
        style = MaterialTheme.typography.displayLarge.merge(
            TextStyle(
                color = Color(0xFF000000),
                drawStyle = Stroke(
                    width = 5f
                )
            )
        )
    )

    Text(
        text = text,
        color = Color(0xFF5BCAD8),
        style = MaterialTheme.typography.displayLarge
    )


}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    OutLineTextTheme {
        Greeting()
    }
}