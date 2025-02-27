package com.example.btexample.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

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
}