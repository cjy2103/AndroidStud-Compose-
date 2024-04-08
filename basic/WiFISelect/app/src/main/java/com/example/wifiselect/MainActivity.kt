package com.example.wifiselect

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getSystemService
import com.example.wifiselect.ui.data.WifiInfo
import com.example.wifiselect.ui.data.WifiInfoProvider
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

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val wifiList = remember {
            mutableStateListOf<WifiInfo>()
        }

        Button(
            onClick = {
                wifiList.clear()
                wifiList.addAll(WifiInfoProvider().filerList(context))
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

        WiFiList(wifiList)

    }
}

@Composable
fun WiFiList(wifiList: SnapshotStateList<WifiInfo>) {
    val context = LocalContext.current.applicationContext
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 20.dp)
    ) {
        items(wifiList.size) { index ->
            WifiItem(wifiInfo = wifiList[index]){
                if(it.second){
                    Log.v("잠김","잠겼다리")
                    WifiInfoProvider().wifiConnect(it.first,context)

                } else {
                    val info = it.first
                    Log.v("안잠겼다리","${info.SSID}")
                    WifiInfoProvider().wifiOpenConnect(it.first,context)
                }
            }
        }
    }
}

@Composable
fun WifiItem(wifiInfo: WifiInfo, onItemClick: (Pair<WifiInfo,Boolean>) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .border(1.dp, Color(0xFFBABABA))
    ) {
        val signIcon = when(wifiInfo.level){
            in 0 downTo -60 -> R.drawable.wifi_good
            in -61 downTo -70 -> R.drawable.wifi_normal
            else -> R.drawable.wifi_bad
        }
        val isWifiLock = wifiInfo.capabilities.let { WifiInfoProvider().isCapability(it) }
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .clickable {
                    onItemClick(Pair(wifiInfo,isWifiLock))
                }
        ) {
            Image(painter = painterResource(id = signIcon), contentDescription = null )
            if(isWifiLock){
                Image(
                    modifier = Modifier.padding(start = 5.dp),
                    painter = painterResource(id = R.drawable.lock),
                    contentDescription = null)
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "SSID: ${wifiInfo.SSID}")
            Text(
                modifier = Modifier.padding(start = 5.dp),
                text = WifiInfoProvider().parseFrequency(wifiInfo.frequency)
            )
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