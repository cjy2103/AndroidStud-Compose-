package com.example.wifiselect

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 20.dp)
    ) {
        items(wifiList.size) { index ->
            WifiItem(wifiInfo = wifiList[index])
        }
    }
}

@Composable
fun WifiItem(wifiInfo: WifiInfo) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .border(1.dp, Color(0xFFBABABA))
    ) {
        Text(text = "SSID: ${wifiInfo.SSID}")
    }

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    WiFISelectTheme {
        Greeting()
    }
}