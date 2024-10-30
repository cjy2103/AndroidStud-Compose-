package com.example.btexample.navigation

sealed class ScreenNavItem(val route : String){
    object Home : ScreenNavItem("Home")
    object Scenario : ScreenNavItem("Scenario")
    object Move : ScreenNavItem("Move")
    object Test : ScreenNavItem("Test")
    object Bluetooth : ScreenNavItem("Bluetooth")
}