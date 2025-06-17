package com.example.launcher.ui.component

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

@Composable
fun AppItem(app: ApplicationInfo, packageManager: PackageManager, onClick: (String) -> Unit) {
    val icon = remember {
        packageManager.getApplicationIcon(app.packageName)
    }
    val appName = remember {
        packageManager.getApplicationLabel(app).toString()
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(app.packageName) }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(icon),
            contentDescription = appName,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = appName, fontSize = 18.sp)
    }
}