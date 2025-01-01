package com.example.btexample.ui.presentation.moveScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.btexample.ui.presentation.bluetoothScreen.BluetoothDialog
import com.example.btexample.ui.presentation.bluetoothScreen.BluetoothViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("MissingPermission")
@Composable
fun MoveScreen(
    navController: NavHostController,
    bluetoothViewModel: BluetoothViewModel
) {
    val connectedDevice = bluetoothViewModel.connectedDevices.firstOrNull()
    val currentSpeed by bluetoothViewModel.currentSpeed.collectAsState()

    var isDialogOpen by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top Row for connection status and button
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

        // Central Control Section (Joystick and Buttons)
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = 100.dp), // Add bottom space for + - buttons
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Joystick Buttons
            ArrowButton(
                icon = Icons.Default.KeyboardArrowUp,
                onLongClick = { bluetoothViewModel.sendDataToDevice("<ACMTN3FWD>") },
                onRelease = { bluetoothViewModel.sendDataToDevice("<ACMTN4STOP>") },
                enableHapticFeedback = true
            )

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                ArrowButton(
                    icon = Icons.Default.KeyboardArrowLeft,
                    onLongClick = { bluetoothViewModel.sendDataToDevice("<ACMTN3CCW>") },
                    onRelease = { bluetoothViewModel.sendDataToDevice("<ACMTN4STOP>") },
                    enableHapticFeedback = true
                )

                Spacer(modifier = Modifier.width(16.dp))

                ArrowButton(
                    icon = Icons.Default.KeyboardArrowRight,
                    onLongClick = { bluetoothViewModel.sendDataToDevice("<ACMTN2CW>") },
                    onRelease = { bluetoothViewModel.sendDataToDevice("<ACMTN4STOP>") },
                    enableHapticFeedback = true
                )
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 46.dp), // 16dp 좌우 + 30dp 추가 높이
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Decrease Speed Button
            Button(
                onClick = { bluetoothViewModel.decreaseSpeed() },
                modifier = Modifier.size(56.dp), // 크기 조정
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.LightGray,
                    contentColor = Color.Black
                )
            ) {
                Text("-", fontSize = 25.sp)            }

            // Current Speed Text
            Text(
                text = "$currentSpeed",
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 16.dp),
            )

            // Increase Speed Button
            Button(
                onClick = { bluetoothViewModel.increaseSpeed() },
                modifier = Modifier.size(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.LightGray,
                    contentColor = Color.Black
                ),
                contentPadding = PaddingValues(0.dp) // 기본 패딩 제거
            ) {
                Text("+", fontSize = 25.sp)
            }
        }
    }
}


@Composable
fun ArrowButton(
    icon: ImageVector,
    onLongClick: () -> Unit, // LongClick 동작
    onRelease: () -> Unit,   // 손을 뗐을 때 동작
    enableHapticFeedback: Boolean = true // 진동 활성화 여부 (기본값: true)
) {
    val haptic = LocalHapticFeedback.current // Haptic Feedback 객체
    var isPressed by remember { mutableStateOf(false) } // 버튼 상태 관리
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .size(96.dp) // 버튼 크기
            .clip(CircleShape)
            .background(Color.White)
            .border(BorderStroke(3.dp, Color.Black), CircleShape)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        if (!isPressed) { // Long Press 이벤트가 한 번만 발생하도록
                            isPressed = true
                            onLongClick() // Bluetooth 데이터 전송
                            coroutineScope.launch {
                                while (isPressed) { // 진동은 버튼 누르는 동안 계속 발생
                                    if (enableHapticFeedback) {
                                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    }
                                    delay(100) // 진동 간격
                                }
                            }
                        }
                    },
                    onPress = {
                        try {
                            awaitRelease() // 손 뗄 때까지 대기
                            if (isPressed) {
                                isPressed = false
                                onRelease() // Release 이벤트 호출 (BT 데이터 전송)
                            }
                        } catch (_: Exception) {
                            isPressed = false // 예외 발생 시 상태 초기화
                        }
                    }
                )
            },
        contentAlignment = Alignment.Center // 아이콘을 중앙에 정렬
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier.size(48.dp) // 아이콘 크기
        )
    }
}