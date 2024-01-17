package com.example.room

import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.room.ui.theme.RoomTheme
import com.example.room.vm.MainViewModel

class MainActivity : ComponentActivity() {
    private lateinit var viewModel : MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setContent {
            RoomTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting(viewModel: MainViewModel, modifier: Modifier = Modifier) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = viewModel.text.value,
            modifier = modifier
        )


        Row(){
            Button(
                onClick = { viewModel.saveData("저장 클릭") },
                modifier = Modifier
                    .padding(top = 40.dp)
                    .size(width = 150.dp, height = 40.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF56E1F3),
                    contentColor = Color.Black)

            ) {
                Text(text = "저장")
            }

            Spacer(modifier = Modifier.width(20.dp)) // 버튼 사이의 간격 20dp

            Button(
                onClick = { viewModel.deleteData("조회 클릭") },
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
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    RoomTheme {
        Greeting(MainViewModel()) // viewModel 전달
    }
}