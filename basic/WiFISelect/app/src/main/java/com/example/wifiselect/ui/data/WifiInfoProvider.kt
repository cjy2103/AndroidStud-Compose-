package com.example.wifiselect.ui.data

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.net.wifi.WifiNetworkSpecifier
import android.net.wifi.WifiNetworkSuggestion
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

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


    @SuppressLint("ServiceCast", "MissingPermission")
    fun wifiConnect(wifiInfo: WifiInfo, context: Context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            val networkPass = ""


            val wifiNetworkSpecifier: WifiNetworkSpecifier?


            wifiNetworkSpecifier = WifiNetworkSpecifier.Builder().setSsid(wifiInfo.SSID)
                .setWpa2Passphrase(networkPass).build()




            val networkRequest = NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .removeCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .setNetworkSpecifier(wifiNetworkSpecifier)
                .build()

            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

            val networkCallback = object : ConnectivityManager.NetworkCallback() {
                override fun onUnavailable() {
                    super.onUnavailable()

                    Log.v("11","111")
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    Log.v("222","2222")
                }

                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    connectivityManager?.bindProcessToNetwork(network)
                    Log.v("333","3333")

                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    Log.v("444","44444")
                }
            }

            connectivityManager?.requestNetwork(networkRequest, networkCallback)


//            val networkSuggestion = WifiNetworkSuggestion.Builder()
//                .setSsid(wifiInfo.SSID)
//                .setWpa2Passphrase("DECS1234!")
//                .
//                .build()
//
//            val networkSuggestionsList = listOf(networkSuggestion)
//
//            val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
//
//            val status = wifiManager.addNetworkSuggestions(networkSuggestionsList)
//
//            if (status == WifiManager.STATUS_NETWORK_SUGGESTIONS_SUCCESS) {
//                // WiFi 네트워크 제안이 성공적으로 추가되었으면 연결을 시도합니다.
//                val intent = Intent(WifiManager.ACTION_PICK_WIFI_NETWORK)
////                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
////                context.startActivity(intent)
//                Log.v("그래","ㅇㅇㅇㅇㅇㅇ")
//
//            } else {
//                // WiFi 네트워크 제안 추가에 실패한 경우 처리 로직을 추가합니다.
//                // 예를 들어 사용자에게 알림을 표시할 수 있습니다.
//                Log.v("안돼","ㅇㅇㅇㅇㅇㅇ")
//            }

        } else {
            val wifiConfig = WifiConfiguration().apply {
                SSID = "\"${wifiInfo.SSID}\""
                allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE) // 오픈 WiFi 설정
//                preSharedKey = "\"$key\""
            }

            val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
            // 네트워크 추가
            val netId = wifiManager.addNetwork(wifiConfig)
            wifiManager.disconnect()
            wifiManager.enableNetwork(netId, true)
            wifiManager.reconnect()
        }
    }

    @SuppressLint("ServiceCast", "MissingPermission")
    fun wifiOpenConnect(wifiInfo: WifiInfo, context: Context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            val networkSpecifier = WifiNetworkSpecifier.Builder()
//                .setSsid(wifiInfo.SSID)
//                .setWpa2Passphrase(null.toString()) // 비밀번호 없는 open 네트워크이므로 null 설정
//                .build()
//
//            val networkRequest = NetworkRequest.Builder()
//                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
//                .setNetworkSpecifier(networkSpecifier)
//                .build()
//
//            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//            val networkCallback = object : ConnectivityManager.NetworkCallback() {
//                override fun onAvailable(network: Network) {
//                    // WiFi 네트워크 사용 가능 시 동작할 내용을 여기에 구현
//                    super.onAvailable(network)
//                    // 예: 네트워크 사용 가능 시 처리
//                }
//
//                override fun onUnavailable() {
//                    // WiFi 네트워크 사용 불가능 시 동작할 내용을 여기에 구현
//                    super.onUnavailable()
//                    // 예: 네트워크 사용 불가능 시 처리
//                }
//            }
//
//            connectivityManager.requestNetwork(networkRequest, networkCallback)

            val wifiNetworkSpecifier: WifiNetworkSpecifier?

                wifiNetworkSpecifier =
                    WifiNetworkSpecifier.Builder().setSsid(wifiInfo.SSID).build()

            val networkRequest = NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .setNetworkSpecifier(wifiNetworkSpecifier)
                .build()

            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

            val networkCallback = object : ConnectivityManager.NetworkCallback() {

                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    connectivityManager?.bindProcessToNetwork(network)
                }

                override fun onLost(network: Network) {
                    super.onLost(network)

                }
            }

            connectivityManager?.requestNetwork(networkRequest, networkCallback)

            } else {
            val wifiConfig = WifiConfiguration().apply {
                SSID = "\"${wifiInfo.SSID}\""
                allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE) // 오픈 WiFi 설정
            }

            val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
            // 네트워크 추가
            val netId = wifiManager.addNetwork(wifiConfig)
            wifiManager.disconnect()
            wifiManager.enableNetwork(netId, true)
            wifiManager.reconnect()
        }
    }
}

