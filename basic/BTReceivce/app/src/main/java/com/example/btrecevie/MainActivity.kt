package com.example.btrecevie

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import com.example.btrecevie.provider.UsbToUart
import java.io.IOException
import java.io.InputStream
import java.util.UUID
class MainActivity : ComponentActivity() {
    private val TAG = "BluetoothServer"
    private val APP_NAME = "BTReceiverApp"
    private val MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

    private lateinit var bluetoothAdapter: BluetoothAdapter
    private var acceptThread: AcceptThread? = null
    val receivedMessages = MutableLiveData<String>() // LiveData로 UI와 연동

    private lateinit var usbToUart: UsbToUart
    private lateinit var usbManager: UsbManager
    private lateinit var usbReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        usbManager = getSystemService(Context.USB_SERVICE) as UsbManager
        usbToUart = UsbToUart(this)

        // BroadcastReceiver 등록
        usbReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if ("com.example.USB_PERMISSION" == intent.action) {
                    val device = intent.getParcelableExtra<UsbDevice>(UsbManager.EXTRA_DEVICE)
                    val granted = intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)
                    if (granted && device != null) {
                        usbToUart.setSelectedDevice(device)
                        Log.d("USB", "Permission granted for device: $device")
                    } else {
                        Log.d("USB", "USB permission denied for device: $device")
                    }
                }
            }
        }
        registerReceiver(usbReceiver, IntentFilter("com.example.USB_PERMISSION"))

        // 부팅 시 USB 권한 요청
        requestUsbPermissions()

        setContent {
            MessageReceiverScreen(this)
        }

        // BluetoothAdapter 초기화
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter

        if (bluetoothAdapter == null) {
            Log.e(TAG, "Device does not support Bluetooth")
            Toast.makeText(this, "Bluetooth not supported on this device", Toast.LENGTH_LONG).show()
            return
        }

        if (!bluetoothAdapter.isEnabled) {
            Log.d(TAG, "Bluetooth is not enabled")
        }

        // 수신 대기 쓰레드 시작
        acceptThread = AcceptThread()
        acceptThread?.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        acceptThread?.cancel()
    }

    private fun requestUsbPermissions() {
        val devices = usbManager.deviceList.values
        val ch340Device = devices.find { it.vendorId == 6790 && it.productId == 29987 }

        if (ch340Device != null) {
            val permissionIntent = PendingIntent.getBroadcast(
                this,
                0,
                Intent("com.example.USB_PERMISSION"),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            if (!usbManager.hasPermission(ch340Device)) {
                Log.d("USB", "Requesting permission for CH340 device.")
                usbManager.requestPermission(ch340Device, permissionIntent)
            } else {
                Log.d("USB", "Permission already granted for CH340 device.")
                usbToUart.setSelectedDevice(ch340Device)
            }
        } else {
            Log.e("USB", "CH340 device not found.")
        }
    }

    private inner class AcceptThread : Thread() {
        @SuppressLint("MissingPermission")
        private val serverSocket: BluetoothServerSocket? =
            try {
                bluetoothAdapter.listenUsingRfcommWithServiceRecord(APP_NAME, MY_UUID)
            } catch (e: IOException) {
                Log.e(TAG, "Socket's listen() method failed", e)
                null
            }

        @SuppressLint("MissingPermission")
        override fun run() {
            while (true) {
                val socket: BluetoothSocket? = try {
                    Log.d(TAG, "Waiting for connection...")
                    serverSocket?.accept()
                } catch (e: IOException) {
                    Log.e(TAG, "Socket's accept() method failed", e)
                    break
                }

                socket?.also {
                    Log.d(TAG, "Connection accepted from ${it.remoteDevice.name}")
                    Thread { manageConnectedSocket(it) }.start() // 각 연결을 별도 쓰레드에서 처리
                }
            }
        }

        fun cancel() {
            try {
                serverSocket?.close()
            } catch (e: IOException) {
                Log.e(TAG, "Could not close the connect socket", e)
            }
        }
    }

    private fun manageConnectedSocket(socket: BluetoothSocket) {
        val inputStream: InputStream = socket.inputStream
        val buffer = ByteArray(1024)

        try {
            while (true) {
                val bytes = inputStream.read(buffer)
                val message = String(buffer, 0, bytes)
                Log.d(TAG, "Received: $message")

                // LiveData를 통해 UI에 반영
                receivedMessages.postValue(message)

                // UART로 전송
                val selectedDevice = usbToUart.getOrDetectCh340Device()
                if (selectedDevice != null) {
                    val permissionIntent = PendingIntent.getBroadcast(
                        this,
                        0,
                        Intent("com.example.USB_PERMISSION"),
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )
                    usbToUart.requestPermission(selectedDevice, permissionIntent)
                    val result = usbToUart.sendAndReceiveData(selectedDevice, message)
                    Log.d("BluetoothToUSB", "UART result: $result")
                } else {
                    Log.e("BluetoothToUSB", "No CH340 device detected.")
                }
            }
        } catch (e: IOException) {
            Log.e(TAG, "Error during communication with client", e)
        } finally {
            try {
                socket.close()
            } catch (e: IOException) {
                Log.e(TAG, "Error while closing socket", e)
            }
        }
    }
}

@Composable
fun MessageReceiverScreen(viewModel: MainActivity) {
    val messages by viewModel.receivedMessages.observeAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Received Messages:", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(8.dp))
        messages?.let {
            Text(it, style = MaterialTheme.typography.bodySmall)
        } ?: Text("No messages received yet.", style = MaterialTheme.typography.displaySmall)
    }
}
