package com.example.myapplication.ui.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.common.ApiResult
import com.example.myapplication.data.model.TestResponse

@Composable
fun ApiTestScreen(modifier: Modifier = Modifier, viewModel : TestViewModel = hiltViewModel()) {

    val testList by viewModel.testList.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp) // 추가적인 여백을 줄 수 있음
    ) {
        Text(
            text = when (testList) {
                is ApiResult.Success -> "API 요청 성공!"
                is ApiResult.Failure -> "API 요청 실패!"
                else -> "⌛ 요청 중..."
            },
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        when (testList) {
            is ApiResult.Success -> {
                LazyColumn {
                    items((testList as ApiResult.Success<List<TestResponse>>).data) { item ->
                        Text(
                            text = "[ ID: ${item.id} ]  |  ${item.word}",
                            fontSize = 18.sp,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }
            }
            is ApiResult.Failure -> {
                Text("API 요청에 실패했습니다.", color = Color.Red)
            }
            else -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
    }
}
