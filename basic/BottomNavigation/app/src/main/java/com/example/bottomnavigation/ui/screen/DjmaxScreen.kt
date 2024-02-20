package com.example.bottomnavigation.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bottomnavigation.R
import com.example.bottomnavigation.ui.theme.BottomNavigationTheme
import com.example.bottomnavigation.ui.vm.DjmaxViewModel

@Composable
fun DjmaxScreen(modifier: Modifier = Modifier){

    val viewModel : DjmaxViewModel = viewModel()

    Column(
        modifier = modifier
            .padding(top = 20.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(100.dp))

        val buttonClicked = viewModel.buttonClicked

        if(buttonClicked.value){
            Image(painter = painterResource(id = R.drawable.iv_djmax_alice),
                contentDescription = "Djmax",
                modifier = Modifier.size(250.dp,250.dp)
            )
        } else {
            Image(painter = painterResource(id = R.drawable.iv_djmax_x_mas),
                contentDescription = "Djmax",
                modifier = Modifier.size(250.dp,250.dp)
            )
        }



        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Djmax 화면")

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = { viewModel.clickButton() },
            modifier = Modifier
                .size(150.dp,50.dp)
            ,
            border = BorderStroke(2.dp,Color.Black),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFA8EAE7),
                contentColor = Color.Black),
        ) {
            Text(text = "Button")
        }



    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DjmaxPreView() {
    BottomNavigationTheme {
        DjmaxScreen()
    }
}