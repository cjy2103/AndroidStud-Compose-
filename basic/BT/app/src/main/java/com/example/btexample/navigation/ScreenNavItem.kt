package com.example.btexample.navigation

sealed class ScreenNavItem(val route : String){
    object Permission : ScreenNavItem("Permission")
    object Home : ScreenNavItem("Home")
    object Scenario : ScreenNavItem("Scenario")
    object ScenarioSelect : ScreenNavItem("ScenarioSelect")
    object LemmyCall : ScreenNavItem("LemmyCall")
    object LemmyNightEscort : ScreenNavItem("LemmyNightEscort")
    object LemmyEmergency : ScreenNavItem("LemmyEmergency")
    object Move : ScreenNavItem("Move")
    object Test : ScreenNavItem("Test")
    object Bluetooth : ScreenNavItem("Bluetooth")
}