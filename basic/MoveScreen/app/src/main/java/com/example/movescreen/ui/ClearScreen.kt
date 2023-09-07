package com.example.movescreen.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.movescreen.R

 @Composable
fun ClearScreen(navController: NavHostController = rememberNavController()) {

     Column(modifier = Modifier.padding(top = 20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Clear 화면"
        )

        Spacer(modifier = Modifier.height(50.dp))


        Image(
            painterResource(id = R.drawable.clear),
            contentDescription  = "클리어",
            modifier = Modifier
                .width(250.dp)
                .height(250.dp)
        )

        Spacer(modifier = Modifier.height(50.dp))

        Button(onClick = { navController.navigate("FailScreen") }) {

        }

    }

}
