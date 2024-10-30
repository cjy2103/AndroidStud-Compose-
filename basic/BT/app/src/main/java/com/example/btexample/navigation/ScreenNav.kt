package com.example.btexample.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.btexample.ui.presentation.bluetoothScreen.BluetoothScreen
import com.example.btexample.ui.presentation.homeScreen.HomeScreen
import com.example.btexample.ui.presentation.moveScreen.MoveScreen
import com.example.btexample.ui.presentation.sinarioScreen.ScenarioScreen
import com.example.btexample.ui.presentation.testScreen.TestScreen


@Composable
fun ScreenHost(navController: NavHostController) {
    NavHost(navController, startDestination = ScreenNavItem.Home.route,
        enterTransition = {
            EnterTransition.None
        },
        exitTransition = {
            ExitTransition.None
        } ){

        composable(ScreenNavItem.Home.route){
            HomeScreen(navController)
        }

        composable(ScreenNavItem.Scenario.route){
            ScenarioScreen(navController)
        }

        composable(ScreenNavItem.Move.route) {
            MoveScreen(navController)
        }

        composable(ScreenNavItem.Test.route){
            TestScreen()
        }

        composable(ScreenNavItem.Bluetooth.route){
            BluetoothScreen()
        }

    }
}