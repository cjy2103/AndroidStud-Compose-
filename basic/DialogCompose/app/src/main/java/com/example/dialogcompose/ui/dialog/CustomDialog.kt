package com.example.dialogcompose.ui.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dialogcompose.R
import com.example.dialogcompose.ui.theme.DialogComposeTheme

@Composable
fun CustomDialog(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(
                LinearGradient(
                    colors = listOf(Color.White, Color.Cyan),
                    start = Alignment.TopCenter,
                    end = Alignment.BottomCenter
                )
            )
    ) {
        Spacer(modifier = Modifier.width(50.dp))
        Image(
            painterResource(id = R.drawable.blue_archive),
            contentDescription = "블루아카이브")
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CustomDialogPreView() {
    DialogComposeTheme {
        CustomDialog()
    }
}