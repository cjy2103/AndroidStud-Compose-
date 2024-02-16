package com.example.bottomnavigation.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.compose.ui.platform.WindowInfo

object SystemUtil {

    /**
     * @DESC: 상단바 투명
     * @param window
     * @param constraintLayout
     */
    fun statusbarSetting(window: Window, view : View) {
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS) // 주석치면 아이콘색 흰색
        }
        else {
            view.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR // 아이콘색 검정으로 -> 주석하면 흰색
        }


    }

    /**
     * @DESC: SoftBottomBar 숨기기
     * @param window
     */
    fun sofNavigationBarHide(window: Window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            if (window.insetsController != null) {
                window.insetsController!!.hide(WindowInsets.Type.navigationBars())
                window.insetsController!!.systemBarsBehavior =
                    WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        }
    }


    fun findActivity(context: Context): Activity {
        var currentContext = context
        while (currentContext is ContextWrapper) {
            if (currentContext is Activity) return currentContext
            currentContext = currentContext.baseContext
        }
        throw IllegalStateException("Permissions should be called in the context of an Activity")
    }


}