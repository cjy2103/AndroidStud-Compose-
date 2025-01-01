package com.example.btexample.ui.presentation.sinarioScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.btexample.ui.presentation.bluetoothScreen.BluetoothDialog
import com.example.btexample.ui.presentation.bluetoothScreen.BluetoothViewModel
@SuppressLint("MissingPermission")
@Composable
fun LemmyNightEscortScreen(
    navController: NavHostController,
    bluetoothViewModel: BluetoothViewModel
) {
    // 버튼 상태를 리스트로 관리
    val buttonStates = remember { mutableStateListOf(false, false, false, false) }

    // Dialog 상태 관리
    var isDialogOpen by remember { mutableStateOf(false) }
    val connectedDevice = bluetoothViewModel.connectedDevices.firstOrNull()

    // 버튼 컴포넌트를 재사용하기 위한 함수
    @Composable
    fun ScenarioButton(
        index: Int,
        buttonText: String,
        onClick: () -> Unit
    ) {
        Button(
            onClick = {
                buttonStates[index] = true // 클릭된 버튼 상태를 true로 설정
                onClick()
            },
            enabled = !buttonStates[index], // 클릭된 버튼 비활성화
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (buttonStates[index]) Color.Gray else Color.Cyan,
                contentColor = Color.Black
            )
        ) {
            Text(text = buttonText, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
    }

    // 화면 중앙에 배치
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // 전체 화면 배경색 설정
            .padding(top = 50.dp, start = 16.dp, end = 16.dp, bottom = 16.dp) // 화면 여백
    ) {
        // 최상단 Bluetooth 상태 표시 및 연결 버튼
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 연결 상태 텍스트
            Text(
                text = when (connectedDevice) {
                    null -> "연결된 디바이스 없음"
                    else -> "연결된 기기: ${connectedDevice.name} (${connectedDevice.address})"
                },
                modifier = Modifier.weight(1f),
            )

            // "디바이스 연결" 버튼
            Button(
                onClick = { isDialogOpen = true },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(text = if (connectedDevice == null) "디바이스 연결" else "다른 디바이스 연결")
            }
        }

        // BluetoothDialog 표시
        if (isDialogOpen) {
            BluetoothDialog(
                onDismissRequest = { isDialogOpen = false },
                onDevicePaired = { device ->
                    bluetoothViewModel.connectToDevice(device)
                    isDialogOpen = false
                },
                bluetoothViewModel = bluetoothViewModel
            )
        }

        // 버튼 목록
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 래미 호출 버튼
            ScenarioButton(
                index = 0,
                buttonText = "래미 호출",
                onClick = { bluetoothViewModel.sendDataToDevice("<APCMCALLNIGHTLEMMY>") }
            )

            // 화장실 이동 버튼
            ScenarioButton(
                index = 1,
                buttonText = "화장실 이동",
                onClick = { bluetoothViewModel.sendDataToDevice("<APCMMOVENIGHTBATH>") }
            )

            // 침실 이동 버튼
            ScenarioButton(
                index = 2,
                buttonText = "침실 이동",
                onClick = { bluetoothViewModel.sendDataToDevice("<APCMMOVENIGHTROOM>") }
            )

            // 복귀 버튼
            ScenarioButton(
                index = 3,
                buttonText = "복귀",
                onClick = {
                    bluetoothViewModel.sendDataToDevice("<APCMNIGHTBACK>")
                }
            )
        }
    }
}