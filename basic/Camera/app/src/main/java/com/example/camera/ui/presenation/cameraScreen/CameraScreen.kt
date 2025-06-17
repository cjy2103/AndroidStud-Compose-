package com.example.camera.ui.presenation.cameraScreen

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.graphics.Matrix
import android.graphics.SurfaceTexture
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CaptureRequest
import android.hardware.camera2.TotalCaptureResult
import android.media.ImageReader
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.Surface
import android.view.TextureView
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.camera.camera.setupCamera
import com.example.camera.navigation.ScreenNavItem
import com.example.camera.util.clearOcrCacheFiles
import java.io.ByteArrayOutputStream
import java.io.File

@Composable
fun CameraScreen(navController: NavController) {
    val context = LocalContext.current
    val cameraManager = ContextCompat.getSystemService(context, CameraManager::class.java)!!

    LaunchedEffect(Unit) {
        clearOcrCacheFiles(context)
    }

    val previewSurface = remember { mutableStateOf<Surface?>(null) }
    val cameraDeviceRef = remember { mutableStateOf<CameraDevice?>(null) }
    val sessionRef = remember { mutableStateOf<CameraCaptureSession?>(null) }

    val imageReader = remember {
        ImageReader.newInstance(1920, 1080, ImageFormat.JPEG, 1).apply {
            setOnImageAvailableListener({ reader ->
                val image = reader.acquireLatestImage() ?: return@setOnImageAvailableListener
                val buffer = image.planes[0].buffer
                val bytes = ByteArray(buffer.remaining())
                buffer.get(bytes)
                image.close()

                val originalBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                val matrix = Matrix().apply { postRotate(90f) }
                val rotatedBitmap = Bitmap.createBitmap(
                    originalBitmap,
                    0, 0,
                    originalBitmap.width,
                    originalBitmap.height,
                    matrix,
                    true
                )

                val rotatedBytes = ByteArrayOutputStream().use { stream ->
                    rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                    stream.toByteArray()
                }

                val tempFile = File.createTempFile("ocr_", ".jpg", context.cacheDir)
                tempFile.outputStream().use { it.write(rotatedBytes) }

                // ✅ 1. Uri 문자열을 안전하게 인코딩
                val fileUri = Uri.fromFile(tempFile)
                val route = ScreenNavItem.Preview.createRoute(fileUri.toString())

                // ✅ 2. NavController로 이동 (등록된 preview 화면으로)
                navController.navigate(route)

            }, null)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .height(1000.dp)
                .graphicsLayer { scaleX = -1f },
            factory = { ctx ->
                TextureView(ctx).apply {
                    if (isAvailable) {
                        setupCamera(context, this, cameraManager, previewSurface, cameraDeviceRef, sessionRef, imageReader)
                    } else {
                        surfaceTextureListener = object : TextureView.SurfaceTextureListener {
                            override fun onSurfaceTextureAvailable(texture: SurfaceTexture, width: Int, height: Int) {
                                setupCamera(context, this@apply, cameraManager, previewSurface, cameraDeviceRef, sessionRef, imageReader)
                            }

                            override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {}
                            override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean = true
                            override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {}
                        }
                    }
                }
            }
        )

        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                val device = cameraDeviceRef.value ?: return@Button
                val session = sessionRef.value ?: return@Button

                val captureRequest = device.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE).apply {
                    addTarget(imageReader.surface)
                    set(CaptureRequest.JPEG_ORIENTATION, 0)
                }

                session.capture(captureRequest.build(), null, null)
            }
        ) {
            Text("촬영")
        }
    }
}