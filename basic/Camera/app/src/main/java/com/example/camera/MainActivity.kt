package com.example.camera

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.rememberNavController
import com.example.camera.navigation.ScreenHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 0)
        setContent {
            val navController = rememberNavController()
            ScreenHost(navController = navController)
        }
    }
}
