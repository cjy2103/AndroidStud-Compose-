package com.example.btexample.ui.presentation.testScreen


import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.util.Log
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
import com.example.btexample.ui.presentation.bluetoothScreen.BluetoothViewModel

@SuppressLint("MissingPermission")
@Composable
fun TestScreen(modifier: Modifier = Modifier){
    // BluetoothViewModel을 사용
    val bluetoothViewModel: BluetoothViewModel = hiltViewModel()

    val testViewModel: TestViewModel = hiltViewModel()

    // Dialog 상태 관리
    var isDialogOpen by remember { mutableStateOf(false) }

    // 연결된 장치를 표시하기 위한 상태
    val connectedDevice = bluetoothViewModel.connectedDevices.firstOrNull()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = modifier.height(30.dp))

        // 연결된 기기 정보 표시
        Text(text = "연결된 기기: ${connectedDevice?.name ?: "없음"}")

        Spacer(modifier = Modifier.height(10.dp))

        // 디바이스 찾기 버튼 (Dialog 열기)
        Button(
            onClick = {
                isDialogOpen = true
            },
            modifier = Modifier
                .size(width = 150.dp, height = 40.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFA4C5DF),
                contentColor = Color.Black
            ),
            shape = RectangleShape
        ) {
            Text(text = "디바이스 찾기")
        }

        // BluetoothDialog
        if (isDialogOpen) {
            BluetoothDialog(
                onDismissRequest = { isDialogOpen = false },
                onDevicePaired = { device ->
                    // BluetoothDialog에서 선택한 장치를 연결 시도
                    bluetoothViewModel.connectToDevice(device)
                    isDialogOpen = false
                },
                bluetoothViewModel = bluetoothViewModel
            )
        }

        // 연결된 장치 정보 표시
        connectedDevice?.let {
            Text("Connected to: ${it.name} (${it.address})")
            // 연결된 장치가 있으면 BT 통신 로직 추가
        } ?: run {
            Text("연결된 디바이스 없음")
        }

        Spacer(modifier = Modifier.height(100.dp))

        // 텍스트 입력 필드
        TextField(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(4.dp)
                )
                .size(300.dp, 50.dp),
            value = testViewModel.textState.value,
            onValueChange = { value ->
                testViewModel.onTextChanged(value) // 텍스트 상태 업데이트
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
            )
        )

        Spacer(modifier = Modifier.height(50.dp))

        // 데이터 전송 버튼
        Button(
            onClick = {
                bluetoothViewModel.sendDataToDevice(testViewModel.textState.value)
            },
            modifier = Modifier
                .size(width = 150.dp, height = 50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFA4C5DF),
                contentColor = Color.Black
            ),
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