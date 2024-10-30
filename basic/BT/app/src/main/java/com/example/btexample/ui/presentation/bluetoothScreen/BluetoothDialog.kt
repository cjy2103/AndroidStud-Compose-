package com.example.btexample.ui.presentation.bluetoothScreen

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel

@SuppressLint("MissingPermission")
@Composable
fun BluetoothDialog(
    onDismissRequest: () -> Unit,
    onDevicePaired : (BluetoothDevice) -> Unit,
    bluetoothViewModel : BluetoothViewModel = hiltViewModel()
){
    // rememberLauncherForActivityResult를 사용하여 권한 요청 런처를 Compose 내부에서 사용
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            bluetoothViewModel.startScan() // 권한이 허용되면 Bluetooth 스캔을 시작
        } else {
            // 권한이 거부된 경우 필요한 처리 (예: 사용자에게 알림)
        }
    }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.White, shape = RoundedCornerShape(8.dp))
        ) {
            Text(
                text = "Bluetooth Device Scanner",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Scan")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Paired Devices:", modifier = Modifier.padding(16.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 200.dp) // 팝업 크기 제한
                    .padding(16.dp)
            ) {
                items(bluetoothViewModel.pairedDevices.size) { index ->
                    val device = bluetoothViewModel.pairedDevices[index]
                    val deviceInfo = "${device.name} (${device.address})"

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable {
                                bluetoothViewModel.pairDevice(device)
                            }
                    ) {
                        Text(deviceInfo, modifier = Modifier.padding(8.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { onDismissRequest() },
                modifier = Modifier.align(Alignment.End).padding(16.dp)
            ) {
                Text("Close")
            }
        }
    }
}