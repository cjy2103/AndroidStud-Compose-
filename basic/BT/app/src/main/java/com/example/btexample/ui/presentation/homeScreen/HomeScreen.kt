package com.example.btexample.ui.presentation.homeScreen

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.btexample.navigation.ScreenNavItem

@Composable
fun HomeScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(top = 50.dp, start = 20.dp)
            .fillMaxSize()
    ){
        Spacer(modifier = Modifier.height(30.dp))


        Row {
            Button(onClick = {
                navController.navigate(ScreenNavItem.Scenario.route){

                }
            },
                modifier = Modifier
                    .size(width = 150.dp, height = 150.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF5722),
                    contentColor = Color.Black),
                contentPadding = PaddingValues(0.dp) // 패딩 제거

            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center // Text를 중앙에 정렬
                ) {
                    Text(text = "시나리오 실행 화면")
                }            }
            Spacer(modifier.width(10.dp))
            Button(onClick = {
                navController.navigate(ScreenNavItem.Move.route)
            },
                modifier = Modifier
                    .size(width = 150.dp, height = 150.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00BCD4),
                    contentColor = Color.Black),
                contentPadding = PaddingValues(0.dp) // 패딩 제거

            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center // Text를 중앙에 정렬
                ) {
                    Text(text = "래미 구동 화면")
                }            }

        }

        Spacer(modifier = Modifier.height(30.dp))

        Row {

            Button(onClick = {
                navController.navigate(ScreenNavItem.Test.route)
            },
                modifier = Modifier
                    .size(width = 150.dp, height = 150.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF8BC34A),
                    contentColor = Color.Black),
                contentPadding = PaddingValues(0.dp) // 패딩 제거

            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center // Text를 중앙에 정렬
                ) {
                    Text(text = "BT 테스트 화면")
                }            }

            Spacer(modifier = Modifier.width(10.dp))
            
            Button(onClick = {
                navController.navigate(ScreenNavItem.Bluetooth.route)
            },
                modifier = Modifier
                    .size(width = 150.dp, height = 150.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFEB3B),
                    contentColor = Color.Black),
                contentPadding = PaddingValues(0.dp) // 패딩 제거

            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center // Text를 중앙에 정렬
                ) {
                    Text(text = "BT 스캔 확인")
                }            }

        }

    }
}