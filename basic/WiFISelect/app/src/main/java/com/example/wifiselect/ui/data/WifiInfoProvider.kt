package com.example.wifiselect.ui.data

import android.annotation.SuppressLint
import android.content.Context
import android.net.wifi.WifiManager
import android.util.Log

class WifiInfoProvider {

    fun filerList(context: Context): List<WifiInfo> {
        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager

        @SuppressLint("MissingPermission")
        val wifiList = wifiManager.scanResults

//        val wifiInfo = wifiManager.connectionInfo
//
//        Log.d("Wi-Fi 정보", "SSID: ${wifiInfo.ssid}")
//        Log.d("Wi-Fi 정보", "BSSID: ${wifiInfo.bssid}")
//        Log.d("Wi-Fi 정보", "신호 강도: ${wifiInfo.rssi}")
//        Log.d("Wi-Fi 정보", "연결 속도: ${wifiInfo.linkSpeed}")
//        Log.d("Wi-Fi 정보", "IP 주소: ${wifiInfo.ipAddress}")
//        Log.d("Wi-Fi 정보", "MAC 주소: ${wifiInfo.macAddress}")
//        Log.d("Wi-Fi 정보", "보안 유형: ${wifiInfo.currentSecurityType}")

        // 로그 출력
        for (wifi in wifiList) {
            Log.d(
                "testtest",
                WifiInfo(
                    wifi.SSID,
                    wifi.BSSID,
                    wifi.level,
                    wifi.capabilities,
                    wifi.frequency
                ).toString()
            )
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

        return filteredWifiList.sortedByDescending { it.level }

    }

    fun isCapability(capability : String) : Boolean {
        return capability.contains("WPA") || capability.contains("WPA2")
                || capability.contains("WEP")
    }

    fun parseFrequency(frequency : Int) : String{
        return if(frequency in 2400..2484){
            "2.4G"
        } else if (frequency >= 5000){
            "5G"
        } else {
            ""
        }
    }



}