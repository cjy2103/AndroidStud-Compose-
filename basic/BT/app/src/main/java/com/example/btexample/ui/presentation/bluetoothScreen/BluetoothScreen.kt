package com.example.btexample.ui.presentation.bluetoothScreen

import android.Manifest
import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@SuppressLint("MissingPermission")
@Composable
fun BluetoothScreen(
    modifier: Modifier = Modifier,
){
    val bluetoothViewModel: BluetoothViewModel = hiltViewModel()

    // rememberLauncherForActivityResult를 사용하여 권한 요청 런처를 Compose 내부에서 사용
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // 권한이 허용되면 Bluetooth 스캔을 시작
            bluetoothViewModel.startScan()
        } else {
            // 권한이 거부된 경우 필요한 처리 (예: 사용자에게 알림)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        Text(text = "Bluetooth Device Scanner", modifier = Modifier.padding(bottom = 16.dp))

        // Scan 버튼
        Button(
            onClick = {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp)
        ) {
            Text("Scan")
        }

        // 스캔된 페어링된 디바이스 목록 표시
        Text("Paired Devices:", modifier = Modifier.padding(bottom = 8.dp))

        LazyColumn {
            items(bluetoothViewModel.pairedDevices.size) { index ->
                val device = bluetoothViewModel.pairedDevices[index]
                val deviceInfo = "${device.name} (${device.address})"

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable {
                        bluetoothViewModel.pairDevice(device)
                    }) {
                    Text(deviceInfo, modifier = Modifier.padding(end = 8.dp))
                }
            }
        }
    }
}