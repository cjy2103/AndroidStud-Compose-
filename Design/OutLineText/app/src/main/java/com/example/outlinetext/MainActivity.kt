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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

    val mapleFont = FontFamily(
        Font(R.font.font_maple)
    )

    Column(modifier.fillMaxWidth().height(200.dp)) {
        Box(
            modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(Color(0xFF00BCD4))
                .padding(top = 30.dp),
            contentAlignment = Alignment.Center
        ) {

            TextOutLine(text = text, innerColor = Color(0xFFFFEB3B)
                , outlineColor = Color(0xFF000000),5f)

        }
    }






}

@Preview(showBackground = true, widthDp = 400, heightDp = 760)
@Composable
fun GreetingPreview() {
    OutLineTextTheme {
        Greeting()
    }
}