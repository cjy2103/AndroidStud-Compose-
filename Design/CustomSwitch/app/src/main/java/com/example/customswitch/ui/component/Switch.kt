package com.example.customswitch.ui.component


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.customswitch.R
import com.example.customswitch.ui.theme.BlueSky
import com.example.customswitch.ui.theme.BorderColor
import com.example.customswitch.ui.theme.NightSky

@Composable
fun CustomSwitch(checked : Boolean){
    val switchWidth = 160.dp
    val switchHeight = 64.dp
    val handleSize = 52.dp
    val handlePadding = 10.dp


    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .width(switchWidth)
            .height(switchHeight)
            .clip(RoundedCornerShape(switchHeight))
            .background(if (checked) NightSky else BlueSky)
            .border(3.dp, BorderColor, RoundedCornerShape(switchHeight))
    ) {
        val backgroundPainter = painterResource(id = R.drawable.background)
        Canvas(modifier = Modifier.fillMaxSize()) {
            with(backgroundPainter){
                val scale = size.width / intrinsicSize.width
                val scaleHeight = intrinsicSize.height * scale
                translate(top = if(checked) 0f else size.height - scaleHeight) {
                    draw(Size(size.width, scaleHeight))
                }
            }
        }

        Box(modifier = Modifier
            .padding(horizontal = handlePadding)
            .size(handleSize)
            .offset(x = if (checked) switchWidth - handleSize - handlePadding * 2f else 0.dp)
            .paint(painterResource(id = R.drawable.sun))
            Image(painter = painterResource(id = ))
        )



    }



}


@Preview(showBackground = true)
@Composable
fun CustomSwitchDarkPreview() {
    CustomSwitch(true)
}

@Preview(showBackground = true)
@Composable
fun CustomSwitchPreview() {
    CustomSwitch(false)
}

