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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movescreen.R
import com.example.movescreen.ui.theme.MoveScreenTheme

@Composable
fun FailScreen(navController: NavHostController = rememberNavController()) {
    Column(
        modifier = Modifier.padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Fail 화면"
        )

        Spacer(modifier = Modifier.height(50.dp))


        Image(
            painterResource(id = R.drawable.fail),
            contentDescription = "페일",
            modifier = Modifier
                .width(250.dp)
                .height(250.dp)
        )

        Spacer(modifier = Modifier.height(50.dp))

        Button(onClick = { navController.navigate("ClearScreen") }) {
            Text(text = "Clear 화면 이동")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FailPreview() {
    MoveScreenTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            FailScreen()
        }
    }
}