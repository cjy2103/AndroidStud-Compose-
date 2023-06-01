package com.example.textview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.textview.ui.theme.TextViewTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TextViewTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    val mapleFont = FontFamily(
        Font(R.font.font_maple)
    )

    val mapleLight = FontFamily(
        Font(R.font.maplestory_light)
    )

    Column(modifier = modifier.padding(start = 20.dp, top = 20.dp, end = 20.dp, bottom = 20.dp)) {
        Text(
            text = "Hello $name!",
            fontSize = dpToSp(dp = 20.dp)
        )
        Text(
            text = "메이플스토리 볼드체",
            modifier = modifier.padding(top = 20.dp),
            fontSize = dpToSp(dp = 20.dp),
            color = Color(0xFFFF5722),
            fontFamily = mapleFont
        )
        Text(
            text = "메이플스토리 라이트체",
            modifier = modifier.padding(top = 20.dp),
            fontSize = dpToSp(dp = 20.dp),
            color = Color(0xFF03A9F4),
            fontFamily = mapleLight
        )

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    TextViewTheme {
        Greeting("Android")
    }
}

@Composable
fun dpToSp(dp: Dp) = with(LocalDensity.current) { dp.toSp() }


