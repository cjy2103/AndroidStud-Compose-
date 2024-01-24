package com.example.restart


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.restart.ui.theme.ReStartTheme
import com.example.restart.util.MyExceptionHandler


class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Thread.setDefaultUncaughtExceptionHandler(
            MyExceptionHandler(
                this,
                MainActivity::class.java
            )
        )

        setContent {
            ReStartTheme {
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
    Column(modifier = Modifier
        .padding(top = 40.dp)
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
        ) {

        Text(
            text = "예외 테스트",
            modifier = modifier
        )

        Button(
            onClick = {
                throw NullPointerException("Simulated Exception")
            },
            modifier = Modifier
                .padding(top = 40.dp)
                .size(width = 150.dp, height = 40.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF56E1F3),
                contentColor = Color.Black)

        ) {
            Text(text = "예외 발생")
        }

    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    ReStartTheme {
        Greeting()
    }
}