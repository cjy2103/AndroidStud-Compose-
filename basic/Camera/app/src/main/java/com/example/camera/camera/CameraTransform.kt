package com.example.camera.camera

import android.graphics.Matrix
import android.graphics.RectF
import android.hardware.camera2.CameraManager
import android.view.Surface
import android.view.TextureView

fun configureTransform(view: TextureView, width: Int, height: Int, cameraId: String, cameraManager: CameraManager) {
    val matrix = Matrix()
    val rotation = view.display?.rotation ?: Surface.ROTATION_0
    val characteristics = cameraManager.getCameraCharacteristics(cameraId)
    val sensorOrientation = characteristics.get(android.hardware.camera2.CameraCharacteristics.SENSOR_ORIENTATION) ?: 0

    val viewRect = RectF(0f, 0f, width.toFloat(), height.toFloat())
    val bufferRect = RectF(0f, 0f, height.toFloat(), width.toFloat())
    val centerX = viewRect.centerX()
    val centerY = viewRect.centerY()

    bufferRect.offset(centerX - bufferRect.centerX(), centerY - bufferRect.centerY())
    matrix.setRectToRect(viewRect, bufferRect, Matrix.ScaleToFit.FILL)

    val rotationDegrees = when (rotation) {
        Surface.ROTATION_0 -> 0
        Surface.ROTATION_90 -> 90
        Surface.ROTATION_180 -> 180
        Surface.ROTATION_270 -> 270
        else -> 0
    }

    val totalRotation = (sensorOrientation - rotationDegrees + 360) % 360
    matrix.postRotate(totalRotation.toFloat(), centerX, centerY)

    // 좌우 반전
//    matrix.postScale(-1f, 1f, centerX, centerY)

    view.setTransform(matrix)
}
