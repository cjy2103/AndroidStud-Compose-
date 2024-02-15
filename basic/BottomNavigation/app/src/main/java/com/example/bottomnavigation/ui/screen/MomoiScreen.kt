package com.example.bottomnavigation.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.bottomnavigation.R

@Composable
fun MomoiScreen(
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .padding(top = 20.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(100.dp))

        Image(painter = painterResource(id = R.drawable.iv_momoi),
            contentDescription = "Momoi",
            modifier = Modifier.size(250.dp,250.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Momoi 화면")


    }
}