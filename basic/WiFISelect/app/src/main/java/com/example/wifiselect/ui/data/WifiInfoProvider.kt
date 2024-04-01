package com.example.wifiselect.ui.data

import android.annotation.SuppressLint
import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

class WifiInfoProvider() {

    fun filerList(context: Context): ArrayList<WifiInfo> {
        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        @SuppressLint("MissingPermission")
        val wifiList = wifiManager.scanResults

        val wifiInfo = wifiManager.connectionInfo

        Log.d("Wi-Fi 정보", "SSID: ${wifiInfo.ssid}")
        Log.d("Wi-Fi 정보", "BSSID: ${wifiInfo.bssid}")
        Log.d("Wi-Fi 정보", "신호 강도: ${wifiInfo.rssi}")
        Log.d("Wi-Fi 정보", "연결 속도: ${wifiInfo.linkSpeed}")
        Log.d("Wi-Fi 정보", "IP 주소: ${wifiInfo.ipAddress}")
        Log.d("Wi-Fi 정보", "MAC 주소: ${wifiInfo.macAddress}")
//        Log.d("Wi-Fi 정보", "보안 유형: ${wifiInfo.currentSecurityType}")

        // 로그 출력
        for (wifi in wifiList) {
            Log.d("testtest",
                WifiInfo(
                    wifi.SSID,
                    wifi.BSSID,
                    wifi.level,
                    wifi.capabilities,
                    wifi.frequency
                ).toString())
            wifi.capabilities
        }

        var filteredWifiList = ArrayList<WifiInfo>()


        for (wifi in wifiList) {
            val existingWifiInfo = filteredWifiList.find { it.SSID == wifi.SSID }
            if (existingWifiInfo != null) {
                if (wifi.level > existingWifiInfo.level) {
                    filteredWifiList.remove(existingWifiInfo)
                    filteredWifiList.add(
                        WifiInfo(
                            wifi.SSID,
                            wifi.BSSID,
                            wifi.level,
                            wifi.capabilities,
                            wifi.frequency
                        )
                    )
                }
            } else {
                filteredWifiList.add(
                    WifiInfo(
                        wifi.SSID,
                        wifi.BSSID,
                        wifi.level,
                        wifi.capabilities,
                        wifi.frequency
                    )
                )
            }
        }

        Log.v("결과는?", filteredWifiList.toString())
        Log.v("길이?", filteredWifiList.size.toString())

        return filteredWifiList

    }



}