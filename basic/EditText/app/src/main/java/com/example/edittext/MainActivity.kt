package com.example.edittext

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.edittext.ui.theme.EditTextTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EditTextTheme {
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

    val textState = remember {
        mutableStateOf("")
    }

    val searchResult = remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = textState.value,
            onValueChange = {
                value -> textState.value = value
            },
            placeholder = {
                Text(text = "글을 입력하세요")
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier.border(
                width = 1.dp,
                color = Color.Gray, // 테두리 색상
                shape = RoundedCornerShape(4.dp) // 테두리 모양
            )
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                searchResult.value = textState.value
            },
            modifier = Modifier
                .size(width = 150.dp, height = 40.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF56E1F3),
                contentColor = Color.Black)

        ) {
            Text(text = "Button")
        }

        Spacer(modifier = Modifier.height(40.dp))

        Text(text = searchResult.value)

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EditTextTheme {
        Greeting()
    }
}