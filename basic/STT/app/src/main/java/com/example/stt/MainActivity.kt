package com.example.stt

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stt.ui.theme.STTTheme
import com.example.stt.vm.SpeechToTextViewModel
import com.example.stt.vm.SpeechToTextViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            STTTheme {
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
    val context = LocalContext.current

    val viewModel : SpeechToTextViewModel = viewModel(
        factory = SpeechToTextViewModelFactory(context.applicationContext as Application)
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.height(200.dp))

        Text(text = "Result: ${viewModel.sttText}")

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = { viewModel.startSpeechToText() },
            modifier = Modifier
                .size(width = 150.dp, height = 40.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF56E1F3),
                contentColor = Color.Black)

        ) {
            Text(text = "Button")
        }


    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    STTTheme {
        Greeting()
    }
}