package com.example.btexample

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf 
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.example.btexample.navigation.PermissionScreenHost
import com.example.btexample.navigation.ScreenHost
import com.example.btexample.ui.theme.BTExampleTheme
import com.example.btexample.ui.presentation.bluetoothScreen.BluetoothViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        enableEdgeToEdge()
        setContent {
            BTExampleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val navController = rememberNavController()


                    // 권한 상태를 관리하는 remember
                    val permissionsGranted = remember { mutableStateOf(false) }

                    // 권한 요청 런처
                    val permissionLauncher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.RequestMultiplePermissions()
                    ) { permissions ->
                        permissionsGranted.value = permissions.values.all { it }
                    }

                    // 권한 확인 및 요청
                    LaunchedEffect(Unit) {
                        val requiredPermissions = mutableListOf<String>().apply {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                                add(Manifest.permission.BLUETOOTH_SCAN)
                                add(Manifest.permission.BLUETOOTH_ADVERTISE)
                                add(Manifest.permission.BLUETOOTH_CONNECT)
                            }
                            add(Manifest.permission.ACCESS_FINE_LOCATION)
                            add(Manifest.permission.ACCESS_COARSE_LOCATION)
                        }

                        val missingPermissions = requiredPermissions.filter {
                            ContextCompat.checkSelfPermission(
                                this@MainActivity,
                                it
                            ) != PackageManager.PERMISSION_GRANTED
                        }

                        if (missingPermissions.isNotEmpty()) {
                            // 권한 요청
                            permissionLauncher.launch(missingPermissions.toTypedArray())
                        } else {
                            // 권한 모두 부여됨
                            permissionsGranted.value = true
                        }
                    }

                    // 권한 상태에 따라 적절한 Host 표시
                    if (permissionsGranted.value) {
                        ScreenHost(navController = navController) // Bluetooth 관련 화면
                    } else {
                        PermissionScreenHost(navController = navController) // 권한 관련 화면
                    }
                }
            }
        }
    }
}

