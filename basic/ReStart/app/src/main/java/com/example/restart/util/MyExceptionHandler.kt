package com.example.restart.util

import android.content.Context
import android.content.Intent
import android.os.Process
import java.io.PrintWriter
import java.io.StringWriter
import kotlin.system.exitProcess

class MyExceptionHandler(
    private val myContext: Context,
    private val myActivityClass: Class<*>
) : Thread.UncaughtExceptionHandler {
    override fun uncaughtException(thread: Thread, exception: Throwable) {
        val stackTrace = StringWriter()
        exception.printStackTrace(PrintWriter(stackTrace))
        System.err.println(stackTrace) // You can use LogCat too
        val intent = Intent(myContext, myActivityClass)
        val s = stackTrace.toString()
        //you can use this String to know what caused the exception and in which Activity
        intent.putExtra("uncaughtException", "Exception is: $stackTrace")
        intent.putExtra("stacktrace", s)
        myContext.startActivity(intent)
        //for restarting the Activity
        Process.killProcess(Process.myPid())
        exitProcess(0)
    }
}