package com.example.uart

import android.app.Application
import android.hardware.usb.UsbManager
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hoho.android.usbserial.driver.UsbSerialPort
import com.hoho.android.usbserial.driver.UsbSerialProber
import com.hoho.android.usbserial.util.SerialInputOutputManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

class UartViewModel(application: Application) : AndroidViewModel(application) {
    private val usbManager = application.getSystemService(UsbManager::class.java)
    private var serialPort: UsbSerialPort? = null
    private var ioManager: SerialInputOutputManager? = null

    private val _receivedData = MutableStateFlow("")
    val receivedData = _receivedData.asStateFlow()

    fun initializeConnection() {
        viewModelScope.launch {
            val availableDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(usbManager)
            if (availableDrivers.isNotEmpty()) {
                val driver = availableDrivers[0]
                val connection = usbManager.openDevice(driver.device)
                serialPort = driver.ports[0]
                serialPort?.open(connection)
                serialPort?.setParameters(9600, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE)

                ioManager = SerialInputOutputManager(serialPort, serialInputOutputListener)
                Executors.newSingleThreadExecutor().submit(ioManager)
            }
        }
    }

    private val serialInputOutputListener = object : SerialInputOutputManager.Listener {
        override fun onNewData(data: ByteArray) {
            val receivedString = String(data)
            viewModelScope.launch {
                _receivedData.emit(receivedString)
            }
        }

        override fun onRunError(e: Exception) {
            viewModelScope.launch {
                _receivedData.emit("Error: ${e.message}")
            }
        }
    }

    fun sendData(data: String) {
        viewModelScope.launch(Dispatchers.IO) {
            serialPort?.write(data.toByteArray(), 1000)
        }
    }

    override fun onCleared() {
        ioManager?.stop()
        serialPort?.close()
        super.onCleared()
    }
}