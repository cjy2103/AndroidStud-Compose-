package com.example.camera.ui.presenation.ocrScreen

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import java.io.File

@Composable
fun OcrResultScreen(navController: NavController) {
    val context = LocalContext.current
    val viewModel: OcrViewModel = viewModel()
    val ocrResult by viewModel.ocrResult.observeAsState()

    val uri = navController.currentBackStackEntry
        ?.arguments
        ?.getString("uri")
        ?.let { Uri.parse(it) }

    // OCR 실행
    LaunchedEffect(uri) {
        uri?.let {
            viewModel.processImage(context, it)
        }
    }

    if (ocrResult == null) {
        // 로딩 화면
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator(modifier = Modifier.size(64.dp), strokeWidth = 6.dp)
                Spacer(modifier = Modifier.height(16.dp))
                Text("OCR 결과를 추출 중입니다...", style = MaterialTheme.typography.bodyMedium)
            }
        }
    } else {
        // 결과 UI
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text("OCR 추출 결과", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(16.dp))

            val clList = ocrResult?.result?.images?.firstOrNull()?.result?.cl
            val drugList = clList?.filter { it.category == "처방의약품 명칭" }

            if (!drugList.isNullOrEmpty()) {
                drugList.forEachIndexed { index, field ->
                    val subMap = field.sub?.associateBy { it.category } ?: emptyMap()

                    val dose = subMap["1회 투약량"]?.value ?: "정보 없음"
                    val frequency = subMap["1일 투여횟수"]?.value ?: "정보 없음"
                    val totalDays = subMap["총 투약일수"]?.value ?: "정보 없음"

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F7F7))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "처방의약품 ${index + 1}",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = field.value,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            Text("1회 투약량: $dose", style = MaterialTheme.typography.bodySmall)
                            Text("1일 투여횟수: $frequency", style = MaterialTheme.typography.bodySmall)
                            Text("총 투약일수: $totalDays", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            } else {
                Text("처방의약품 명칭이 없습니다.")
            }
        }
    }
}