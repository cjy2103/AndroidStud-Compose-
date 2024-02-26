package com.example.moduledivide.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.moduledivide.ui.screen.DetailScreen
import com.example.moduledivide.ui.theme.screen.ListScreen
import com.example.moduledivide.ui.vm.CharacterViewModel

@Composable
fun NavigationHost(navController: NavHostController, viewModel: CharacterViewModel){
    NavHost(navController ,startDestination = CharacterScreen.ListScreen.route){
        composable(CharacterScreen.ListScreen.route){
            ListScreen(navController = navController, characterViewModel = viewModel)
        }

        composable("DetailScreen"){
            DetailScreen(viewModel = viewModel)
        }
    }
}