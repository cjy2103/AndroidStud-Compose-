package com.example.btexample.ui.viewModel

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.lifecycle.AndroidViewModel

class BluetoothViewModel(application: Application) : AndroidViewModel(application) {

    private val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    val pairedDevices = mutableStateListOf<BluetoothDevice>()
    val isScanning = mutableStateOf(false)
    val bondedDevices = mutableStateListOf<BluetoothDevice>() // 페어링된 장비 리스트


    private val receiver = object : BroadcastReceiver() {
        @SuppressLint("MissingPermission")
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                BluetoothDevice.ACTION_FOUND -> {
                    val device: BluetoothDevice? = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    device?.let {
                        if (!pairedDevices.contains(it)) {
                            pairedDevices.add(it)
                        }
                    }
                }
                BluetoothDevice.ACTION_BOND_STATE_CHANGED -> {
                    val device: BluetoothDevice? = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    if (device?.bondState == BluetoothDevice.BOND_BONDED) {
                        // 페어링 성공 시, bondedDevices 리스트에 추가
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
    }

    fun startScan() {
        if (isScanning.value) return

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 위치 권한이 없으면 스캔을 시작하지 않습니다.
            return
        }

        isScanning.value = true
        pairedDevices.clear()
        bluetoothAdapter?.startDiscovery()
    }

    @SuppressLint("MissingPermission")
    fun pairDevice(device: BluetoothDevice) {
        device.createBond()  // 페어링 시도
    }

    override fun onCleared() {
        super.onCleared()
        context.unregisterReceiver(receiver)
    }

}