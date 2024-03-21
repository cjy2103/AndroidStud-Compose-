package com.example.retrofit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.lifecycle.ViewModelProvider
import com.example.retrofit.ui.theme.RetrofitTheme
import com.example.retrofit.ui.vm.MainViewModel

class MainActivity : ComponentActivity() {
    private val viewModel : MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RetrofitTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(viewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting(viewModel : MainViewModel,modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = modifier.padding(start = 10.dp,top = 50.dp),
                text = viewModel.data.value
            )
            Button(onClick = { viewModel.dataLoad() },
                modifier = modifier
                    .padding(top = 30.dp)
                    .size(width = 150.dp, height = 40.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF56E1F3),
                    contentColor = Color.Black
                    )
                ) {
                Text(text = "데이터 호출")
            }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    RetrofitTheme {
        Greeting(MainViewModel())
    }
}