package com.example.btexample.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.btexample.ui.presentation.bluetoothScreen.BluetoothScreen
import com.example.btexample.ui.presentation.bluetoothScreen.BluetoothViewModel
import com.example.btexample.ui.presentation.homeScreen.HomeScreen
import com.example.btexample.ui.presentation.moveScreen.MoveScreen
import com.example.btexample.ui.presentation.sinarioScreen.LemmyCallScreen
import com.example.btexample.ui.presentation.sinarioScreen.LemmyEmergencyScreen
import com.example.btexample.ui.presentation.sinarioScreen.LemmyNightEscortScreen
import com.example.btexample.ui.presentation.sinarioScreen.ScenarioScreen
import com.example.btexample.ui.presentation.sinarioScreen.ScenarioSelectScreen
import com.example.btexample.ui.presentation.testScreen.TestScreen


@Composable
fun ScreenHost(navController: NavHostController) {
    val bluetoothViewModel: BluetoothViewModel = hiltViewModel() // 앱 전역 ViewModel
    NavHost(navController, startDestination = ScreenNavItem.Home.route,
        enterTransition = {
            EnterTransition.None
        },
        exitTransition = {
            ExitTransition.None
        } ){

        composable(ScreenNavItem.Home.route){
            HomeScreen(navController,bluetoothViewModel)
        }

        composable(ScreenNavItem.Scenario.route){
            ScenarioScreen(navController,bluetoothViewModel)
        }

        composable(ScreenNavItem.ScenarioSelect.route){
            ScenarioSelectScreen(navController,bluetoothViewModel)
        }

        composable(ScreenNavItem.LemmyCall.route){
            LemmyCallScreen(navController,bluetoothViewModel)
        }

        composable(ScreenNavItem.LemmyNightEscort.route){
            LemmyNightEscortScreen(navController,bluetoothViewModel)
        }

        composable(ScreenNavItem.LemmyEmergency.route){
            LemmyEmergencyScreen(navController,bluetoothViewModel)
        }

        composable(ScreenNavItem.Move.route) {
            MoveScreen(navController,bluetoothViewModel)
        }

        composable(ScreenNavItem.Test.route){
            TestScreen()
        }

        composable(ScreenNavItem.Bluetooth.route){
            BluetoothScreen()
        }

    }
}