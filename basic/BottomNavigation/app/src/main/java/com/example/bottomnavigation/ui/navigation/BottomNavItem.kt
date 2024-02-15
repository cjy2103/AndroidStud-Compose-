package com.example.bottomnavigation.ui.navigation

import android.media.Image
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.bottomnavigation.R

sealed class BottomNavItem(val route : String, val icon : Int, val label : String) {
    object Djmax : BottomNavItem("Djmax", R.drawable.iv_djmax, "Djmax")
    object Momoi : BottomNavItem("Momoi", R.drawable.iv_momoi, "Momoi")
    object Midori : BottomNavItem("Midori", R.drawable.iv_midori, "Midori")
}