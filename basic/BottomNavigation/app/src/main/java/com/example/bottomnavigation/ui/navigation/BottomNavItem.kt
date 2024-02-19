package com.example.bottomnavigation.ui.navigation

import android.media.Image
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.bottomnavigation.R

sealed class BottomNavItem(val route : String, val unselectedIcon : Int, val selectedIcon : Int, val label : String) {
    object Djmax : BottomNavItem("Djmax", R.drawable.iv_djmax,R.drawable.iv_djmax_fail, "Djmax")
    object Momoi : BottomNavItem("Momoi", R.drawable.iv_momoi,R.drawable.iv_alice, "Momoi")
    object Midori : BottomNavItem("Midori", R.drawable.iv_midori,R.drawable.iv_yuse, "Midori")
}