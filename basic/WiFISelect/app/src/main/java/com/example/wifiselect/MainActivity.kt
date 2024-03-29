package com.example.wifiselect

import android.annotation.SuppressLint
import android.content.Context
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.wifiselect.ui.data.WifiInfo
import com.example.wifiselect.ui.theme.WiFISelectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WiFISelectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@SuppressLint("MissingPermission", "NewApi")
@Composable
fun Greeting(modifier: Modifier = Modifier) {

    val context = LocalContext.current

    var text by remember { mutableStateOf("초기값") }


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        
        
        Text(text = text)
        
        Button(
            onClick = {
                val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
                val wifiList = wifiManager.scanResults

                val wifiInfo = wifiManager.connectionInfo

                Log.d("Wi-Fi 정보", "SSID: ${wifiInfo.ssid}")
                Log.d("Wi-Fi 정보", "BSSID: ${wifiInfo.bssid}")
                Log.d("Wi-Fi 정보", "신호 강도: ${wifiInfo.rssi}")
                Log.d("Wi-Fi 정보", "연결 속도: ${wifiInfo.linkSpeed}")
                Log.d("Wi-Fi 정보", "IP 주소: ${wifiInfo.ipAddress}")
                Log.d("Wi-Fi 정보", "MAC 주소: ${wifiInfo.macAddress}")
                Log.d("Wi-Fi 정보", "보안 유형: ${wifiInfo.currentSecurityType}")

                text = wifiInfo.bssid

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
                }

                val filteredList = ArrayList<ScanResult>()

                val groupedBySsid = wifiList.groupBy { it.SSID }

                for ((_, networks) in groupedBySsid) {
                    // RSSI가 가장 높은 항목 선택
                    val maxLevelNetwork = networks.maxByOrNull { it.level }
                    if (maxLevelNetwork != null) {
                        filteredList.add(maxLevelNetwork)
                    }
                }

                Log.v("결과는?", filteredList.toString())
                Log.v("길이?", filteredList.size.toString())

            },
            modifier = Modifier
                .padding(top = 40.dp)
                .size(width = 150.dp, height = 40.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF56E1F3),
                contentColor = Color.Black
            )
        ) {
            Text(text = "Wi-Fi 목록")
        }

    }
}


@SuppressLint("MissingPermission")
fun showWifiList(context : Context){
    Log.v("먼데","222")
    val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
    wifiManager.startScan()
    val result: List<ScanResult> = wifiManager.scanResults

    val descendingList = result.sortedByDescending { it.level }
    descendingList.forEachIndexed { index, data ->
        if (data.SSID != "") {
            Log.d("testtest",
                WifiInfo(
                    data.SSID,
                    data.BSSID,
                    data.level,
                    data.capabilities,
                    data.frequency
                ).toString())
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    WiFISelectTheme {
        Greeting()
    }
}