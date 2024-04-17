package com.example.wifi

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.provider.Settings
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wifi.ui.theme.WifiTheme

class MainActivity : ComponentActivity() {

    private val wifiConnect = mutableStateOf("초기값")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WifiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(wifiConnect)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        checkWifi(this, wifiConnect)
    }
}

@Composable
fun Greeting(wifiConnect: MutableState<String> ,modifier: Modifier = Modifier) {

    val context = LocalContext.current

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = wifiConnect.value,
            modifier = modifier.padding(top = 50.dp)
        )

        Button(
            onClick = { checkWifi(context,wifiConnect) },
            modifier = modifier
                .padding(top = 30.dp)
                .size(width = 150.dp, height = 40.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF56E1F3),
                contentColor = Color.Black
            )
        ) {
            Text(text = "WIFI 연결 확인")
        }


    }

}

fun checkWifi(context: Context, wifiConnect: MutableState<String>){
    val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = manager.activeNetwork

    if(network != null){
        val networkCapabilities = manager.getNetworkCapabilities(network)
        when {
            networkCapabilities!!.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                wifiConnect.value = "WIFI 연결됨"
            }
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                context.startActivities(arrayOf(Intent(Settings.ACTION_WIFI_SETTINGS)))
            }
        }
    } else {
        context.startActivities(arrayOf(Intent(Settings.ACTION_WIFI_SETTINGS)))
        wifiConnect.value = "네트워크 연결 끊어짐"
    }

}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    WifiTheme {
        Greeting(mutableStateOf("초기값"))
    }
}