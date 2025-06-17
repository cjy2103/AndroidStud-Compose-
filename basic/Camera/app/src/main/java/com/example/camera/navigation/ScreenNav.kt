package com.example.camera.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.camera.ui.presenation.cameraScreen.CameraScreen
import com.example.camera.ui.presenation.ocrScreen.OcrResultScreen
import com.example.camera.ui.presenation.preivewScreen.PreviewScreen


@Composable
fun ScreenHost(navController: NavHostController) {
    NavHost(navController, startDestination = ScreenNavItem.Camera.route,
        enterTransition = {
            EnterTransition.None
        },
        exitTransition = {
            ExitTransition.None
        } ){

        composable(ScreenNavItem.Camera.route){
            CameraScreen(navController)
        }

        composable(ScreenNavItem.Preview.route){
            PreviewScreen(navController)
        }

        composable(ScreenNavItem.OcrResult.route){
            OcrResultScreen(navController)
        }

    }
}
