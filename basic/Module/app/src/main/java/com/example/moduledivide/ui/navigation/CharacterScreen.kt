package com.example.moduledivide.ui.navigation

sealed class CharacterScreen(val route: String) {
    data object ListScreen : CharacterScreen("ListScreen")
}