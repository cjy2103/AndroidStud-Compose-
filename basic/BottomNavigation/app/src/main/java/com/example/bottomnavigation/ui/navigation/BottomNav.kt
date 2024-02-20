package com.example.bottomnavigation.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bottomnavigation.ui.screen.DjmaxScreen
import com.example.bottomnavigation.ui.screen.MidoriScreen
import com.example.bottomnavigation.ui.screen.MomoiScreen
import com.example.bottomnavigation.ui.vm.DjmaxViewModel

@Composable
fun NavigationHost(navController: NavHostController
// 추가 parameter : ViewModel의 정보가 담긴 객체 변수
){
    // parameter로 ViewModel이 담김 객체를 가져옴
    NavHost(navController, startDestination = BottomNavItem.Djmax.route,
            enterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                ExitTransition.None
            }
        ){
        composable(BottomNavItem.Djmax.route){
            DjmaxScreen() //ViewModel.djMaxViewModel을 argument로 넘겨줌
        }
        composable(BottomNavItem.Momoi.route){
            MomoiScreen()
        }
        composable(BottomNavItem.Midori.route){
            MidoriScreen()
        }
    }
}