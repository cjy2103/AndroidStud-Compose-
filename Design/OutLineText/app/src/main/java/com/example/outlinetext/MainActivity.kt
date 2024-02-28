package com.example.outlinetext

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
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import com.example.outlinetext.ui.text.FontOutLine
import com.example.outlinetext.ui.text.TextOutLine
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

    Spacer(modifier = modifier.height(20.dp))

    val mapleFont = FontFamily(
        Font(R.font.font_maple)
    )


    Column(modifier.fillMaxSize()) {
        Box(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            contentAlignment = Alignment.Center
        ) {
            TextOutLine(text = text, innerColor = Color(0xFFFFEB3B),
                outlineColor = Color.Black, strokeWidth = 5f)
        }

        Spacer(modifier = modifier.height(20.dp))

        
        Box(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
            , contentAlignment = Alignment.Center
        ) {
            FontOutLine(
                text = "메이플 폰트",
                innerColor = Color(0xFF00BCD4),
                outlineColor = Color.Black,
                font = mapleFont,
                strokeWidth = 5f,
                size = 60.sp
            )

        }
    }



}

@Preview(showBackground = true, widthDp = 400, heightDp = 760)
@Composable
fun GreetingPreview() {
    OutLineTextTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Greeting()
        }
    }
}