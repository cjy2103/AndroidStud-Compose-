package com.example.btexample.ui.presentation.sinarioScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.btexample.ui.presentation.bluetoothScreen.BluetoothDialog
import com.example.btexample.ui.presentation.bluetoothScreen.BluetoothViewModel
@SuppressLint("MissingPermission")
@Composable
fun ScenarioScreen(
    navController: NavHostController,
    bluetoothViewModel: BluetoothViewModel
) {
    val connectedDevice = bluetoothViewModel.connectedDevices.firstOrNull()
    var isDialogOpen by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // 상단 우측 연결 상태 텍스트와 연결 버튼
        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 20.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (connectedDevice != null) {
                    "Connected to: ${connectedDevice.name}"
                } else {
                    "Not connected"
                },
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )

            Button(onClick = { isDialogOpen = true }) {
                Text(text = if (connectedDevice == null) "연결" else "다시 연결")
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

        // 중앙 UI (시나리오 실행 관련 버튼)
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    bluetoothViewModel.sendDataToDevice("<ACCLL4HOME>") // 거실 출발 명령
                },
                modifier = Modifier.size(width = 250.dp, height = 50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00BCD4),
                    contentColor = Color.White
                )
            ) {
                Text(text = "거실 출발", style = MaterialTheme.typography.bodyLarge)
            }

            Button(
                onClick = {
                    bluetoothViewModel.sendDataToDevice("<ACCLL4ROOM>") // 방 출발 명령
                },
                modifier = Modifier.size(width = 250.dp, height = 50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF8BC34A),
                    contentColor = Color.White
                )
            ) {
                Text(text = "방 출발", style = MaterialTheme.typography.bodyLarge)
            }

            Button(
                onClick = {
                    bluetoothViewModel.sendDataToDevice("<ACCLL4BATH>") // 화장실 출발 명령
                },
                modifier = Modifier.size(width = 250.dp, height = 50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFC107),
                    contentColor = Color.Black
                )
            ) {
                Text(text = "화장실 출발", style = MaterialTheme.typography.bodyLarge)
            }

            Button(
                onClick = {
                    bluetoothViewModel.sendDataToDevice("<ACRST0>") // 리셋 명령
                },
                modifier = Modifier.size(width = 250.dp, height = 50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF5722),
                    contentColor = Color.White
                )
            ) {
                Text(text = "리셋", style = MaterialTheme.typography.bodyLarge)
            }


            Button(
                onClick = {
                    bluetoothViewModel.sendDataToDevice("<ACLED2ON>")
                },
                modifier = Modifier.size(width = 250.dp, height = 50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF3F51B5),
                    contentColor = Color.White
                )
            ) {
                Text(text = "LED 온", style = MaterialTheme.typography.bodyLarge)
            }

            Button(
                onClick = {
                    bluetoothViewModel.sendDataToDevice("<ACLED3OFF>")
                },
                modifier = Modifier.size(width = 250.dp, height = 50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF009688),
                    contentColor = Color.White
                )
            ) {
                Text(text = "LED OFF", style = MaterialTheme.typography.bodyLarge)
            }


        }
    }
}