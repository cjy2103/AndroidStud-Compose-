package com.example.dialogcompose

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dialogcompose.ui.dialog.CustomDialog
import com.example.dialogcompose.ui.theme.DialogComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DialogComposeTheme {
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

@SuppressLint("UnrememberedMutableState")
@Composable
fun Greeting() {

    Column(
        modifier = Modifier
            .padding(top = 50.dp)
            .fillMaxSize()
        , horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Dialog Compose 예제",
        )

        Spacer(modifier = Modifier.height(50.dp))

        val buttonClicked = remember { mutableStateOf(false)}

        Button(onClick = {
            buttonClicked.value = true
        }) {
            Text(text = "Dialog Compose 열기")
        }

        if(buttonClicked.value){
            CustomDialog(
                "블루아카이브",
                "나쁜 선생에게는 파봉만준다.",
                "확인",
                "취소",
                {buttonClicked.value = false},
                {buttonClicked.value = false}
            )

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    DialogComposeTheme {
        Greeting()
    }
}