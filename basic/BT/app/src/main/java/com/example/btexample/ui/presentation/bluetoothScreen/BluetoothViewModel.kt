package com.example.btexample.ui.presentation.bluetoothScreen

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.btexample.data.SpeedDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class BluetoothViewModel @Inject constructor(
    application: Application,
    private val dataStore: SpeedDataStore
) : AndroidViewModel(application) {

    private val bluetoothManager = application.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter
    @SuppressLint("StaticFieldLeak")
    private val context = application.applicationContext

    val pairedDevices = mutableStateListOf<BluetoothDevice>()
    val discoveredDevices = mutableStateListOf<BluetoothDevice>()
    val bondedDevices = mutableStateListOf<BluetoothDevice>()
    val connectedDevices = mutableStateListOf<BluetoothDevice>()
    private val activeConnections = mutableMapOf<BluetoothDevice, BluetoothSocket?>()

    private val _currentSpeed = MutableStateFlow(90)
    val currentSpeed: StateFlow<Int> = _currentSpeed.asStateFlow()

    private val receiver = object : BroadcastReceiver() {
        @SuppressLint("MissingPermission")
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                BluetoothDevice.ACTION_FOUND -> {
                    val device: BluetoothDevice? = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    device?.let {
                        if (!discoveredDevices.any { it.address == device.address }) {
                            discoveredDevices.add(device)
                        }
                    }
                }
                BluetoothDevice.ACTION_BOND_STATE_CHANGED -> {
                    val device: BluetoothDevice? = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    if (device?.bondState == BluetoothDevice.BOND_BONDED) {
                        if (!bondedDevices.contains(device)) {
                            bondedDevices.add(device)
                        }
                    }
                }
            }
        }
    }

    init {
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        context.registerReceiver(receiver, filter)

        loadBondedDevices()
        loadSavedSpeed()
    }

    @SuppressLint("MissingPermission")
    fun loadBondedDevices() {
        bondedDevices.clear()
        bondedDevices.addAll(bluetoothAdapter?.bondedDevices?.toList() ?: emptyList())
    }

    fun startScan() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        discoveredDevices.clear()
        bluetoothAdapter?.startDiscovery()
    }

    @SuppressLint("MissingPermission")
    fun pairDevice(device: BluetoothDevice) {
        device.createBond()
    }

    @SuppressLint("MissingPermission")
    fun connectToDevice(device: BluetoothDevice) {
        viewModelScope.launch(Dispatchers.IO) {
            val uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
            val maxRetries = 3
            val retryDelayMillis = 1000L

            var attempt = 0
            var bluetoothSocket: BluetoothSocket? = null

            while (attempt < maxRetries) {
                try {
                    bluetoothSocket = device.createRfcommSocketToServiceRecord(uuid)
                    bluetoothAdapter?.cancelDiscovery()
                    bluetoothSocket.connect()

                    activeConnections[device] = bluetoothSocket
                    if (!connectedDevices.contains(device)) {
                        connectedDevices.add(device)
                    }
                    break
                } catch (e: IOException) {
                    try {
                        bluetoothSocket?.close()
                    } catch (_: IOException) {}
                    attempt++
                    if (attempt < maxRetries) delay(retryDelayMillis)
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun disconnectDevice(device: BluetoothDevice) {
        activeConnections[device]?.apply {
            try {
                close()
            } catch (_: IOException) {}
            finally {
                activeConnections.remove(device)
                connectedDevices.remove(device)
            }
        }
    }

    fun increaseSpeed() {
        if (_currentSpeed.value < 150) {
            val newSpeed = _currentSpeed.value + 10
            _currentSpeed.value = newSpeed
            saveSpeed(newSpeed)
            sendDataToDevice("<ACMSP3INC>")
        } else {
            Toast.makeText(context, "최대속도 150 도달 속도를 더 올릴수 없습니다..", Toast.LENGTH_SHORT).show()
        }
    }

    fun decreaseSpeed() {
        if (_currentSpeed.value > 30) {
            val newSpeed = _currentSpeed.value - 10
            _currentSpeed.value = newSpeed
            saveSpeed(newSpeed)
            sendDataToDevice("<ACMSP3DEC>")
        } else {
            Toast.makeText(context, "최소속도 30도달 속도를 더 줄일수 없습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveSpeed(speed: Int) {
        viewModelScope.launch {
            dataStore.saveSpeed(speed)
        }
    }

    private fun loadSavedSpeed() {
        viewModelScope.launch {
            _currentSpeed.value = dataStore.getSpeed() ?: 90
        }
    }

    @SuppressLint("MissingPermission")
    fun sendDataToDevice(data: String) {
        val connectedDevice = connectedDevices.firstOrNull()
        val socket = activeConnections[connectedDevice]

        if (socket != null && socket.isConnected) {
            try {
                socket.outputStream.write(data.toByteArray())
            } catch (_: IOException) {}
        }
    }

    override fun onCleared() {
        // 연결 유지 (명시적으로 종료하고 싶을 때만 호출)
    }

    fun clearAllConnections() {
        activeConnections.values.forEach { socket ->
            try {
                socket?.close()
            } catch (_: IOException) {}
        }
        activeConnections.clear()
        connectedDevices.clear()
    }
}