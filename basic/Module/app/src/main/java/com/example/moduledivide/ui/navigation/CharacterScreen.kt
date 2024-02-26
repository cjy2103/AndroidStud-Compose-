package com.example.moduledivide.ui.navigation

sealed class CharacterScreen(val route : String, val label : String) {
    data object ListScreen : CharacterScreen("ListScreen","Characters")
    data object DetailScreen : CharacterScreen("DetailScreen","Detail")
}