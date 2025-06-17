package com.example.launcher.ui.presentation

import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.launcher.ui.component.AppItem

@Composable
fun AppLauncherScreen(context: Context, modifier: Modifier = Modifier) {
    val packageManager = context.packageManager

    // 설치된 앱 목록 가져오기 (런처 가능한 앱만 필터링)
    val installedApps = remember {
        packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
            .filter { packageManager.getLaunchIntentForPackage(it.packageName) != null }
    }

    LazyColumn(modifier = modifier) {
        items(installedApps) { app ->
            AppItem(app, packageManager) { packageName ->
                // 클릭하면 해당 앱 실행
                val intent = packageManager.getLaunchIntentForPackage(packageName)
                context.startActivity(intent)
            }
        }
    }
}