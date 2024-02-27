package com.example.outlinetext.ui.text

import android.graphics.Paint.Style
import android.graphics.drawable.LayerDrawable
import android.util.Size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun TextOutLine(text : String, innerColor : Color, outlineColor : Color,
                strokeWidth : Float){
    Text(
        text = text,
        style = MaterialTheme.typography.displayLarge.merge(
            TextStyle(
                color = outlineColor,
                drawStyle = Stroke(
                    width = strokeWidth
                )
            )
        ),
        fontSize = 24.sp
    )

    Text(
        text = text,
        color = innerColor,
        style = MaterialTheme.typography.displayLarge
        ,        fontSize = 24.sp

    )
}

@Composable
fun FontOutLine(text : String, innerColor : Color, outlineColor : Color,
                font : FontFamily,strokeWidth : Float, size : TextUnit){
    Text(
        text = text,
        style = TextStyle(
            color = Color(0xFF000000),
            drawStyle = Stroke(
                width = strokeWidth
            )
        ),
        fontFamily = font,
        fontSize = size
    )

    Text(
        text = text,
        color = Color(0xFF5BCAD8),
        style = TextStyle(
            color = Color(0xFF000000),
            drawStyle = Stroke(
                width = 0f
            )
        ),
        fontFamily = font,
        fontSize = size
    )

}