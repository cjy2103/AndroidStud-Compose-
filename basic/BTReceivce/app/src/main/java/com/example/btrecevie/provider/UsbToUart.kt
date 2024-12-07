package com.example.btrecevie.provider

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.UsbConstants
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbDeviceConnection
import android.hardware.usb.UsbManager
import android.os.Build
import android.util.Log
import com.hoho.android.usbserial.driver.UsbSerialPort
import com.hoho.android.usbserial.driver.UsbSerialProber
import java.io.IOException


class UsbToUart(private val context: Context) {
    private var selectedDevice: UsbDevice? = null
    private val usbManager = context.getSystemService(Context.USB_SERVICE) as UsbManager

    fun getOrDetectCh340Device(): UsbDevice? {
        if (selectedDevice == null) {
            val devices = usbManager.deviceList.values
            selectedDevice = devices.find { it.vendorId == 6790 && it.productId == 29987 }
        }
        return selectedDevice
    }

    fun setSelectedDevice(device: UsbDevice) {
        selectedDevice = device
    }

    fun requestPermission(device: UsbDevice, permissionIntent: PendingIntent) {
        if (!usbManager.hasPermission(device)) {
            usbManager.requestPermission(device, permissionIntent)
        }
    }

    fun sendAndReceiveData(device: UsbDevice, message: String): String {
        val connection = usbManager.openDevice(device)
        if (connection == null) {
            Log.e("USB", "Permission not granted or device unavailable.")
            return "Permission not granted or device unavailable."
        }

        val driver = UsbSerialProber.getDefaultProber().probeDevice(device)
        if (driver == null) {
            Log.d("USB", "No compatible driver found.")
            return "No compatible driver found."
        }

        val port = driver.ports[0] // 첫 번째 포트를 사용
        port.open(connection)
        port.setParameters(
            115200, // Baud rate
            8,      // Data bits
            UsbSerialPort.STOPBITS_1,
            UsbSerialPort.PARITY_NONE
        )

        try {
            // 데이터 송신
            port.write(message.toByteArray(), 1000)

            // 데이터 수신
            val buffer = ByteArray(64)
            val numBytesRead = port.read(buffer, 1000)
            val receivedData = String(buffer, 0, numBytesRead)
            Log.d("USB", "Received: $receivedData")
            return receivedData
        } catch (e: IOException) {
            Log.e("USB", "Error during UART communication", e)
            return "Error during UART communication"
        } finally {
            port.close()
        }
    }
}