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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class BluetoothViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    private val bluetoothManager = application.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter
    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    val pairedDevices = mutableStateListOf<BluetoothDevice>()
    val discoveredDevices = mutableStateListOf<BluetoothDevice>() // 새로 검색된 기기 목록
    val bondedDevices = mutableStateListOf<BluetoothDevice>() // 연결된 장비 리스트

    val connectedDevices = mutableStateListOf<BluetoothDevice>() // 실제 연결된 기기 목록
    private val activeConnections = mutableMapOf<BluetoothDevice, BluetoothSocket?>()

    private val receiver = object : BroadcastReceiver() {
        @SuppressLint("MissingPermission")
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                BluetoothDevice.ACTION_FOUND -> {
                    val device: BluetoothDevice? = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    device?.let {
                        // 중복되지 않는 새 기기만 추가
                        if (!discoveredDevices.any { it.address == device.address }) {
                            discoveredDevices.add(device)
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
        loadBondedDevices()
    }

    @SuppressLint("MissingPermission")
    fun loadBondedDevices() {
        bondedDevices.clear()
        bondedDevices.addAll(bluetoothAdapter?.bondedDevices?.toList() ?: emptyList())
    }

    fun startScan() {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 위치 권한이 없으면 스캔을 시작하지 않습니다.
            return
        }

        discoveredDevices.clear() // 새로 검색된 기기 목록 초기화
        bluetoothAdapter?.startDiscovery()
    }

    @SuppressLint("MissingPermission")
    fun pairDevice(device: BluetoothDevice) {
        device.createBond()  // 페어링 시도
        Log.v("페어링 시도중","페어링")
    }

    @SuppressLint("MissingPermission")
    fun connectToDevice(device: BluetoothDevice) {
        viewModelScope.launch(Dispatchers.IO) {
            // SPP UUID 사용
            val uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
            val maxRetries = 3
            val retryDelayMillis = 1000L

            var attempt = 0
            var bluetoothSocket: BluetoothSocket? = null

            while (attempt < maxRetries) {
                try {
                    Log.v("BluetoothViewModel", "Attempting connection: Attempt $attempt")

                    // 소켓 생성 및 연결
                    bluetoothSocket = device.createRfcommSocketToServiceRecord(uuid)
                    bluetoothAdapter?.cancelDiscovery()
                    bluetoothSocket.connect()

                    // 연결 성공 시 처리
                    activeConnections[device] = bluetoothSocket
                    if (!connectedDevices.contains(device)) {
                        connectedDevices.add(device)
                    }
                    Log.d("BluetoothViewModel", "Successfully connected to ${device.name}")
                    break
                } catch (e: IOException) {
                    Log.e("BluetoothViewModel", "Connection attempt $attempt failed. Retrying...", e)

                    // 연결 실패 시 소켓 닫기
                    try {
                        bluetoothSocket?.close()
                    } catch (closeException: IOException) {
                        Log.e("BluetoothViewModel", "Failed to close socket after connection failure", closeException)
                    }

                    // 재시도
                    attempt++
                    if (attempt < maxRetries) {
                        delay(retryDelayMillis)
                    } else {
                        Log.e("BluetoothViewModel", "Max retries reached. Unable to connect to ${device.name}")
                    }
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun fetchDeviceUuids(device: BluetoothDevice) {
        val filter = IntentFilter(BluetoothDevice.ACTION_UUID)

        // UUID 검색 결과를 수신하는 BroadcastReceiver 등록
        val uuidReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val action = intent.action
                if (BluetoothDevice.ACTION_UUID == action) {
                    val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                    val uuids = device?.uuids

                    if (uuids != null) {
                        // UUID 목록을 로그로 출력
                        uuids.forEach { uuid ->
                            Log.d("BluetoothUUID", "UUID found: ${uuid.uuid}")
                        }
                    } else {
                        Log.d("BluetoothUUID", "No UUIDs found for device: ${device?.name}")
                    }

                    // 검색 완료 후 Receiver 등록 해제
                    context.unregisterReceiver(this)
                }
            }
        }

        // BroadcastReceiver 등록
        context.registerReceiver(uuidReceiver, filter)

        // UUID 검색 요청
        device.fetchUuidsWithSdp()
    }


    @SuppressLint("MissingPermission")
    fun disconnectDevice(device: BluetoothDevice) {
        activeConnections[device]?.apply {
            try {
                close() // Bluetooth 소켓 닫기
            } catch (e: IOException) {
                Log.e("BluetoothViewModel", "Unable to disconnect device: ${device.name}", e)
            } finally {
                activeConnections.remove(device)
                connectedDevices.remove(device)
            }
        }
    }

    // Bluetooth 데이터 전송 메서드
    @SuppressLint("MissingPermission")
    fun sendDataToDevice(data: String) {
        val connectedDevice = connectedDevices.firstOrNull()
        val socket = activeConnections[connectedDevice]

        if (socket != null && socket.isConnected) {
            try {
                socket.outputStream.write(data.toByteArray())
                Log.d("BluetoothViewModel", "Data sent: $data")
            } catch (e: IOException) {
                Log.e("BluetoothViewModel", "Failed to send data: ${e.message}", e)
            }
        } else {
            Log.e("BluetoothViewModel", "No connected device or socket is not connected")
        }
    }


    override fun onCleared() {
        super.onCleared()
        context.unregisterReceiver(receiver)
    }

}