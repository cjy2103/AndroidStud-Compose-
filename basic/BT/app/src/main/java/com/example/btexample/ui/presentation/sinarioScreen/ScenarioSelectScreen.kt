package com.example.btexample.ui.presentation.sinarioScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.btexample.navigation.ScreenNavItem
import com.example.btexample.ui.presentation.bluetoothScreen.BluetoothViewModel

@SuppressLint("MissingPermission")
@Composable
fun ScenarioSelectScreen(
    navController: NavHostController,
    bluetoothViewModel: BluetoothViewModel
) {
    // 화면 중앙에 배치
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // 화면 여백
        verticalArrangement = Arrangement.SpaceEvenly, // 버튼 간격 균등 배치
        horizontalAlignment = Alignment.CenterHorizontally // 중앙 정렬
    ) {
        // 래미 호출 버튼
        Button(
            onClick = {
                navController.navigate(ScreenNavItem.LemmyCall.route)
                      }, // ""으로 이동
            modifier = Modifier
                .fillMaxWidth() // 너비 전체
                .height(150.dp), // 버튼 높이
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00BCD4), // 배경색
                contentColor = Color.White  // 텍스트 색상
            )
        ) {
            Text(text = "래미 호출", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }

        // 야간 동행 버튼
        Button(
            onClick = {
                navController.navigate(ScreenNavItem.LemmyNightEscort.route)
                      }, // "night_escort_screen"으로 이동
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Gray,
                contentColor = Color.White
            )
        ) {
            Text(text = "야간 동행", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }

        // 응급 신고 버튼
        Button(
            onClick = {
                navController.navigate(ScreenNavItem.LemmyEmergency.route)
                      }, // "emergency_call_screen"으로 이동
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                contentColor = Color.White
            )
        ) {
            Text(text = "응급 신고", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
    }
}