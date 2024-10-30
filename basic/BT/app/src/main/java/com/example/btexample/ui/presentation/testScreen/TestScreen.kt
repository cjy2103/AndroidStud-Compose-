package com.example.btexample.ui.presentation.testScreen


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.btexample.ui.presentation.bluetoothScreen.BluetoothDialog

@Composable
fun TestScreen(modifier: Modifier = Modifier){
    val testViewModel : TestViewModel = hiltViewModel()

    var isDialogOpen by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = modifier.height(30.dp))

        Text(text = "연결된 기기 : ${testViewModel.pairDevice.value}")

        Spacer(modifier = Modifier.height(10.dp))


        Button(
            onClick = {
                isDialogOpen = true
            },
            modifier = Modifier
                .size(width = 150.dp, height = 40.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFA4C5DF),
                contentColor = Color.Black),
            shape = RectangleShape

        ) {
            Text(text = "디바이스 찾기")
        }

        if (isDialogOpen) {
            BluetoothDialog(onDismissRequest = { isDialogOpen = false })
        }

        Spacer(modifier = Modifier.height(100.dp))

        TextField(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(4.dp)
                )
                .size(300.dp, 50.dp),
            value = testViewModel.textState.value,
            onValueChange = {
                value -> testViewModel.onTextChanged(value)
            },
            placeholder = {
                Text(text = "여기에 데이터를 입력하세요.")
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
               ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
                capitalization = KeyboardCapitalization.Sentences
                ),
            )

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = {
                //TODO : BT 데이터 Send
            },
            modifier = Modifier
                .size(width = 150.dp, height = 50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFA4C5DF),
                contentColor = Color.Black),
            shape = RectangleShape

        ) {
            Text(text = "BT 데이터 전달")
        }

        Spacer(modifier = Modifier.height(10.dp))



    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TestPreview() {
    TestScreen()
}