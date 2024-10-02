package com.example.uart

import android.hardware.usb.UsbManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp



class MainActivity : ComponentActivity() {

    companion object {
        // Ensure the native library is loaded
        init {
            System.loadLibrary("native-lib")
        }
    }

    external fun stringFromJNI(): String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            UARTCommunicationScreen()
        }
    }



    @Composable
    fun UARTCommunicationScreen() {
        val nativeText = stringFromJNI()

        Text(text = nativeText)

    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        UARTCommunicationScreen()
    }

}