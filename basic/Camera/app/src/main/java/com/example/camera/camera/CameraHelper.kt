package com.example.camera.camera

import android.content.Context
import android.graphics.SurfaceTexture
import android.hardware.camera2.*
import android.util.Log
import android.view.Surface
import android.view.TextureView
import androidx.core.app.ActivityCompat
import android.Manifest
import android.content.pm.PackageManager
import android.media.ImageReader
import androidx.compose.runtime.MutableState


fun setupCamera(
    context: Context,
    textureView: TextureView,
    cameraManager: CameraManager,
    previewSurface: MutableState<Surface?>,
    cameraDeviceRef: MutableState<CameraDevice?>,
    sessionRef: MutableState<CameraCaptureSession?>,
    imageReader: ImageReader
) {
    val texture = textureView.surfaceTexture ?: return
    texture.setDefaultBufferSize(1920, 1080)

    val surface = Surface(texture)
    previewSurface.value = surface

    val cameraId = cameraManager.cameraIdList.firstOrNull {
        val chars = cameraManager.getCameraCharacteristics(it)
        val facing = chars.get(CameraCharacteristics.LENS_FACING)
        Log.d("CameraSelect", "Camera ID: $it, FACING: $facing")
        true
    } ?: return

    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) return

    cameraManager.openCamera(cameraId, object : CameraDevice.StateCallback() {
        override fun onOpened(device: CameraDevice) {
            cameraDeviceRef.value = device
            device.createCaptureSession(listOf(surface, imageReader.surface), object : CameraCaptureSession.StateCallback() {
                override fun onConfigured(session: CameraCaptureSession) {
                    sessionRef.value = session
                    val previewRequestBuilder = device.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW).apply {
                        addTarget(surface)
                    }
                    session.setRepeatingRequest(previewRequestBuilder.build(), null, null)
                }

                override fun onConfigureFailed(session: CameraCaptureSession) {
                    Log.e("Camera2Compose", "Session configuration failed")
                }
            }, null)
        }

        override fun onDisconnected(device: CameraDevice) {
            device.close()
        }

        override fun onError(device: CameraDevice, error: Int) {
            device.close()
        }
    }, null)

    configureTransform(textureView, textureView.width, textureView.height, cameraId, cameraManager)
}
