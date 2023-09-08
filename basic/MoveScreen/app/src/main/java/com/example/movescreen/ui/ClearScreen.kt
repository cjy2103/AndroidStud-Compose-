package com.example.movescreen.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.movescreen.R
import com.example.movescreen.Screen
import com.example.movescreen.ui.theme.MoveScreenTheme

@Composable
fun ClearScreen(
     navController: NavHostController = rememberNavController(),
     modifier: Modifier = Modifier
) {

     Column(
         modifier = modifier.padding(top = 20.dp),
         horizontalAlignment = Alignment.CenterHorizontally
     ) {
        Text(
            text = "Clear 화면"
        )

        Spacer(modifier = modifier.height(50.dp))

        Image(
            painterResource(id = R.drawable.clear),
            contentDescription  = "클리어",
            modifier = modifier
                .width(250.dp)
                .height(250.dp)
        )

        Spacer(modifier = modifier.height(50.dp))

        Button(onClick = { navController.navigate("FailScreen") }) {
            Text(text = "Fail 화면 이동")
        }

    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ClearPreview() {
    MoveScreenTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Screen()
        }
    }
}