package com.example.launcher.ui.presentation

import android.content.Context
import android.content.pm.ApplicationInfo
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.launcher.ui.component.AppItem

@Composable
fun AppGridScreen(context: Context, apps: List<ApplicationInfo>, page: Int) {
    val packageManager = context.packageManager
    val startIndex = page * 6
    val endIndex = minOf(startIndex + 6, apps.size)
    val appsForPage = apps.subList(startIndex, endIndex)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3), // 3x2 = 한 화면에 6개 표시
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            items(appsForPage) { app ->
                AppItem(app, packageManager) { packageName ->
                    val intent = packageManager.getLaunchIntentForPackage(packageName)
                    context.startActivity(intent)
                }
            }
        }
    }
}