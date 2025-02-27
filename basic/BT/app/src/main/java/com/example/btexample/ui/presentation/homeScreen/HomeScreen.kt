package com.example.btexample.ui.presentation.homeScreen

import android.Manifest
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.btexample.navigation.ScreenNavItem
import com.example.btexample.ui.presentation.bluetoothScreen.BluetoothDialog
import com.example.btexample.ui.presentation.bluetoothScreen.BluetoothViewModel

@SuppressLint("MissingPermission")
@Composable
fun HomeScreen(
    navController: NavHostController,
    bluetoothViewModel: BluetoothViewModel = hiltViewModel()
) {
    var isDialogOpen by remember { mutableStateOf(false) }
    val connectedDevice = bluetoothViewModel.connectedDevices.firstOrNull()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(40.dp)) // 위에서 40dp 띄움

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (connectedDevice == null) "연결된 디바이스 없음" else "연결된 기기: ${connectedDevice.name}",
                modifier = Modifier.weight(1f)
            )
            Button(onClick = { isDialogOpen = true }) {
                Text(if (connectedDevice == null) "연결" else "다른 디바이스 연결")
            }
        }

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

        Spacer(modifier = Modifier.height(16.dp))

        // 기존 홈 화면 버튼들
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { navController.navigate(ScreenNavItem.ScenarioSelect.route) },
                    modifier = Modifier
                        .size(width = 150.dp, height = 150.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF5722),
                        contentColor = Color.Black
                    )
                ) {
                    Text(text = "시나리오 실행")
                }

                Button(
                    onClick = { navController.navigate(ScreenNavItem.Move.route) },
                    modifier = Modifier
                        .size(width = 150.dp, height = 150.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00BCD4),
                        contentColor = Color.Black
                    )
                ) {
                    Text(text = "래미 구동")
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { navController.navigate(ScreenNavItem.Test.route) },
                    modifier = Modifier
                        .size(width = 150.dp, height = 150.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF8BC34A),
                        contentColor = Color.Black
                    )
                ) {
                    Text(text = "BT 테스트")
                }

                Button(
                    onClick = { navController.navigate(ScreenNavItem.Scenario.route) },
                    modifier = Modifier
                        .size(width = 150.dp, height = 150.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFEB3B),
                        contentColor = Color.Black
                    )
                ) {
                    Text(text = "UART 통신확인용")
                }
            }
        }
    }
}