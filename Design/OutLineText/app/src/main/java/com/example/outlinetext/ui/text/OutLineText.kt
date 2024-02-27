package com.example.outlinetext.ui.text

import android.graphics.Paint.Style
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle

@Composable
fun TextOutLine(text : String, innerColor : Color, outlineColor : Color){
    Text(
        text = text,
        style = MaterialTheme.typography.displayLarge.merge(
            TextStyle(
                color = outlineColor,
                drawStyle = Stroke(
                    width = 5f
                )
            )
        )
    )

    Text(
        text = text,
        color = innerColor,
        style = MaterialTheme.typography.displayLarge
    )
}