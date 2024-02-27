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
    )

    Text(
        text = text,
        color = innerColor,
        style = MaterialTheme.typography.displayLarge
    )
}

@Composable
fun FontOutLine(text : String, innerColor : Color, outlineColor : Color,
                font : FontFamily,strokeWidth : Float, size : TextUnit){
    Text(
        text = text,
        style = TextStyle(
            color = outlineColor,
            drawStyle = Stroke(
                width = strokeWidth
            )
        ),
        fontFamily = font,
        fontSize = size
    )

    Text(
        text = text,
        color = innerColor,
        fontFamily = font,
        fontSize = size
    )

}