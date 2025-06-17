package com.example.camera.navigation

import android.net.Uri

sealed class ScreenNavItem(val route: String) {
    object Camera : ScreenNavItem("camera")
    object Preview : ScreenNavItem("preview?uri={uri}") {
        fun createRoute(uri: String) = "preview?uri=${Uri.encode(uri)}"
    }
    object OcrResult : ScreenNavItem("ocr?uri={uri}") {
        fun createRoute(uri: String) = "ocr?uri=${Uri.encode(uri)}"
    }
}
