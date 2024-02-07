package com.example.dialogcompose.ui.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.dialogcompose.R
import com.example.dialogcompose.ui.theme.DialogComposeTheme


@Composable
fun CustomDialog(
    title: String,
    message: String = "",
    positiveText: String,
    negativeText: String,
    onClickOk: () -> Unit,
    onClickNo: () -> Unit
){
    Column(
        modifier = Modifier
            .size(320.dp,350.dp)
    ){
        Row(
            modifier = Modifier
                .size(320.dp, 50.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF94E6E9),
                            Color.Cyan
                        )
                    )
                ),
        ) {
            Spacer(modifier = Modifier.width(70.dp))

            Image(
                painterResource(id = R.drawable.blue_archive),
                contentDescription = "블루아카이브")

            Spacer(modifier = Modifier.width(20.dp))

            val mapleFont = FontFamily(
                Font(R.font.font_maple)
            )

            val mapleLight = FontFamily(
                Font(R.font.maplestory_light)
            )

            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = title,
                fontSize = 24.sp,
                fontFamily = mapleFont,
            )
        }

        Column(
            modifier = Modifier
                .size(320.dp,250.dp)

        ) {
            Text(text = message)
        }

        Row (

        ) {
            Button(onClick = { onClickNo() },
                modifier = Modifier.size(

                ),
                shape = RectangleShape

            ) {
                Text(text = negativeText)
            }
            Button(onClick = { onClickOk() }) {
                Text(text = positiveText)
            }
        }

    }


}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CustomDialogPreView() {
    DialogComposeTheme {
        CustomDialog(
            "블루아카이브",
            "나쁜 선생에게는 파봉만준다.",
            "확인",
            "취소",
            {},
            {}
        )
    }
}