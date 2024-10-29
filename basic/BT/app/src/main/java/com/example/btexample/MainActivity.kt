package com.example.btexample

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.btexample.ui.theme.BTExampleTheme
import com.example.btexample.ui.viewModel.BluetoothViewModel
import java.util.UUID

class MainActivity : ComponentActivity() {

    private val bluetoothViewModel: BluetoothViewModel by viewModels()

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                bluetoothViewModel.startScan()
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BTExampleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding),
                        bluetoothViewModel,
                        onRequestPermission = {
                            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                        }
                        )
                }
            }
        }
    }
}

@SuppressLint("MissingPermission")
@Composable
fun Greeting(modifier: Modifier = Modifier, bluetoothViewModel: BluetoothViewModel,
             onRequestPermission: () -> Unit
             ) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(30.dp))

        Text(text = "Bluetooth Device Scanner", modifier = Modifier.padding(bottom = 16.dp))

        // Scan 버튼
        Button(
            onClick = {
                onRequestPermission()
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

        // 페어링된 장비 목록 표시
        Text("Paired Devices:", modifier = Modifier.padding(bottom = 8.dp))
        LazyColumn {
            items(bluetoothViewModel.bondedDevices.size) { index ->
                val device = bluetoothViewModel.bondedDevices[index]
                val deviceInfo = "${device.name} (${device.address})"
                Text(deviceInfo, modifier = Modifier.padding(vertical = 4.dp))
            }
        }

    }
}

