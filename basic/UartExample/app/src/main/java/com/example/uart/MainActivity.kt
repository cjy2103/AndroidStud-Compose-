package com.example.uart

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.uart.ui.theme.UartTheme
import com.hoho.android.usbserial.driver.UsbSerialDriver
import com.hoho.android.usbserial.driver.UsbSerialPort
import com.hoho.android.usbserial.driver.UsbSerialProber

class MainActivity : ComponentActivity() {

    private lateinit var usbReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // BroadcastReceiver 등록
        usbReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                Log.d("USB", "Received broadcast: ${intent.action}")

                if ("com.example.USB_PERMISSION" == intent.action) {
                    Log.d("USB", "Intent Extras: ${intent.extras}")

                    val bundle = intent.extras
                    if (bundle == null) {
                        Log.e("USB", "Intent extras are NULL!")
                        return
                    } else {
                        bundle.keySet()?.forEach {
                            Log.d("USB", "Intent Extra Key: $it -> Value: ${bundle.get(it)}")
                        }
                    }

                    val device: UsbDevice? = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE)
                    val granted = intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)

                    Log.v("USB 권한 결과", "granted: $granted")

                    if (device != null) {
                        Log.d(
                            "USB",
                            "Device Name: ${device.deviceName}, Vendor ID: ${device.vendorId}, Product ID: ${device.productId}"
                        )
                    } else {
                        Log.e("USB", "Received USB device is null. Retrying...")
                        return
                    }

                    if (granted) {
                        Log.d(
                            "USB",
                            "Permission granted for device: Vendor ID=${device.vendorId}, Product ID=${device.productId}"
                        )
                        val receivedData = sendAndReceiveDataFromCh340(device)
                        Log.d("USB", "Received Data: $receivedData")
                    } else {
                        Log.w(
                            "USB",
                            "Permission denied in broadcast. Checking usbManager.hasPermission()..."
                        )

                        val usbManager = context.getSystemService(Context.USB_SERVICE) as UsbManager
                        if (usbManager.hasPermission(device)) {
                            Log.d(
                                "USB",
                                "Permission was actually granted. Proceeding with communication."
                            )
                            val receivedData = sendAndReceiveDataFromCh340(device)
                            Log.d("USB", "Received Data: $receivedData")
                        } else {
                            Log.d("USB", "Permission is truly denied.")
                        }
                    }
                }
            }
        }

        val filter = IntentFilter("com.example.USB_PERMISSION")
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED) // USB 연결 감지

        ContextCompat.registerReceiver(
            this,
            usbReceiver,
            filter,
            ContextCompat.RECEIVER_NOT_EXPORTED
        )

        setContent {
            UartTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    UsbCommunicationScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    @Composable
    fun UsbCommunicationScreen(modifier: Modifier) {
        var receivedData by remember { mutableStateOf("") }
        var statusMessage by remember { mutableStateOf("Idle") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = "UART Communication", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Status: $statusMessage", style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    statusMessage = "Sending data..."
                    val result = handleCh340Communication()
                    if (result != null) {
                        receivedData = result
                        statusMessage = "Data received successfully!"
                    } else {
                        statusMessage = "Failed to communicate with device."
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Send Data")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Received Data: $receivedData",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }

    private var isPermissionRequested = false


    private fun handleCh340Communication(): String? {
        val usbManager = getSystemService(USB_SERVICE) as UsbManager

        val targetDevice = usbManager.deviceList.values.find {
            it.vendorId == 6790 && it.productId == 29987
        }

        if (targetDevice == null) {
            Log.e("USB", "No CH340 device found with Vendor ID=6790, Product ID=29987")
            return null
        }

        Log.d("USB", "CH340 device detected: Vendor ID=${targetDevice.vendorId}, Product ID=${targetDevice.productId}")

        val permissionIntent = PendingIntent.getBroadcast(
            this,
            0,
            Intent("com.example.USB_PERMISSION").apply {
                putExtra(UsbManager.EXTRA_DEVICE, targetDevice) // USB 장치 정보 명확하게 추가
            },
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )


        // 이미 권한이 있는 경우 즉시 통신 시작
        if (usbManager.hasPermission(targetDevice)) {
            Log.d("USB", "Already have permission for CH340 device. Proceeding with communication.")
            return sendAndReceiveDataFromCh340(targetDevice)
        }

        // 이미 요청 중이면 중복 요청 방지
        if (isPermissionRequested) {
            Log.w("USB", "USB Permission request already in progress. Skipping duplicate request.")
            return null
        }

        isPermissionRequested = true

        Log.w("USB", "USB Permission is NOT granted. Requesting permission now...")

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            usbManager.requestPermission(targetDevice, permissionIntent)
            isPermissionRequested = false
        }, 1000) // 1초 후에 권한 요청 (OS가 처리하도록 대기)

        return null
    }

    private fun getCh340Driver(): UsbSerialDriver? {
        val usbManager = getSystemService(USB_SERVICE) as UsbManager
        val availableDevices = usbManager.deviceList.values
        val prober = UsbSerialProber.getDefaultProber()

        for (device in availableDevices) {
            val driver = prober.probeDevice(device)
            if (driver != null && device.vendorId == 6790 && device.productId == 29987) {
                return driver
            }
        }
        return null
    }

    private fun sendAndReceiveDataFromCh340(device: UsbDevice): String? {
        val usbManager = getSystemService(USB_SERVICE) as UsbManager
        val connection = try {
            usbManager.openDevice(device)
        } catch (e: SecurityException) {
            Log.e("USB", "SecurityException: ${e.message}")
            null
        }
        if (connection == null) {
            Log.e("USB", "Failed to open device. Ensure permission is granted.")
            return null
        }

        val driver = UsbSerialProber.getDefaultProber().probeDevice(device)
        if (driver == null) {
            Log.e("USB", "No compatible driver found.")
            return null
        }

        val port = driver.ports[0]
        try {
            port.open(connection)
            port.setParameters(
                115200, // Baud rate
                8,      // Data bits
                UsbSerialPort.STOPBITS_1,
                UsbSerialPort.PARITY_NONE
            )

            // 데이터 송신
            val sendData = "Hello CH340".toByteArray()
            port.write(sendData, 1000)

            // 데이터 수신
            val buffer = ByteArray(64)
            val numBytesRead = port.read(buffer, 1000)
            return String(buffer, 0, numBytesRead)
        } catch (e: Exception) {
            Log.e("USB", "Error during USB communication: ${e.message}")
            return null
        } finally {
            port.close()
        }
    }
}