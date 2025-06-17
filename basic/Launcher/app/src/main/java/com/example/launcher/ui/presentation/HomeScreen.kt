package com.example.launcher.ui.presentation

import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen(context: Context, modifier: Modifier) {
    val packageManager = context.packageManager
    val installedApps = remember {
        packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
            .filter { packageManager.getLaunchIntentForPackage(it.packageName) != null }
    }

    val pages = (installedApps.size / 6) + 1 // 앱 개수에 따라 페이지 계산
    val pagerState = rememberPagerState(initialPage = 0) // 첫 페이지를 1로 설정

    HorizontalPager(
        count = pages + 1, // +1을 추가하여 첫 페이지를 VideoScreen으로
        state = pagerState,
        modifier = modifier.fillMaxSize()
    ) { page ->
        when (page) {
            0 -> VideoScreen() // 첫 번째 페이지에 영상 표시
            else -> AppGridScreen(context, installedApps, page - 1) // 나머지 페이지에 앱 목록 표시
        }
    }
}