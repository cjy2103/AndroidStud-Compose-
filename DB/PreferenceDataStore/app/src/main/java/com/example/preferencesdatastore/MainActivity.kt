package com.example.preferencesdatastore

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.preferencesdatastore.ui.theme.PreferencesDataStoreTheme
import com.example.preferencesdatastore.vm.MainViewModel

class MainActivity : ComponentActivity() {

    private val mainViewModel : MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {
            PreferencesDataStoreTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(mainViewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting(viewModel : MainViewModel, modifier: Modifier = Modifier) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "Preferences DataStore 예제",
            modifier = modifier
        )

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = viewModel.text.value,
            modifier = modifier
        )

        Button(
            onClick = { viewModel.dataSave() },
            modifier = Modifier
                .padding(top = 40.dp)
                .size(width = 150.dp, height = 40.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF56E1F3),
                contentColor = Color.Black)

        ) {
            Text(text = "값 저장")
        }

        Button(
            onClick = { viewModel.dataLoad() },
            modifier = Modifier
                .padding(top = 40.dp)
                .size(width = 150.dp, height = 40.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF56E1F3),
                contentColor = Color.Black)

        ) {
            Text(text = "조회")
        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {

    PreferencesDataStoreTheme {
        Greeting(MainViewModel(Application()))
    }
}