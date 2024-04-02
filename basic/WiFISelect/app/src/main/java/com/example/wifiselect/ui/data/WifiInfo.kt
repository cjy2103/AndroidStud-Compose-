package com.example.wifiselect.ui.data

data class WifiInfo(
    val SSID: String,
    val BSSID: String,
    val level: Int,
    val capabilities: String,
    val frequency: Int){
}
