package com.example.launcher

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.view.Window
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.annotation.RequiresApi


object SystemUtil {

    /**
     * @DESC: window를 얻기 위한 Activity 탐색
     * @param context
     */
    fun findActivity(context: Context) : Activity {
        var currentContext = context
        while (currentContext is ContextWrapper) {
            if (currentContext is Activity) return currentContext
            currentContext = currentContext.baseContext
        }
        throw IllegalStateException("Not an Activity")
    }

    /**
     * @DESC: 상단바 영역 투명 + 해당 영역 사용
     * @param window
     */
    @RequiresApi(Build.VERSION_CODES.R)
    fun statusSetting(window: Window){

        window.insetsController?.hide(WindowInsets.Type.statusBars())

//        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
//
//        window.insetsController?.setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
//            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
    }

    /**
     * @DESC: SoftBottomBar 숨기기
     * @param window
     */
    @RequiresApi(Build.VERSION_CODES.R)
    fun softNavigationBarHide(window: Window){
        window.setDecorFitsSystemWindows(false)
        if(window.insetsController != null){
            window.insetsController!!.hide(WindowInsets.Type.navigationBars())
            window.insetsController!!.systemBarsBehavior =
                WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }



}