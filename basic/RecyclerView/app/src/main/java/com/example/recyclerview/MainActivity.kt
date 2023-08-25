package com.example.recyclerview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.recyclerview.ui.theme.RecyclerViewTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecyclerViewTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("RecyclerView 테스트")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "$name",
            modifier = modifier
        )
    }

    Column(
        modifier = Modifier.padding(top = 60.dp) // 첫 번째 Column의 가로 너비만큼 차지
    ) {
        FruitList()
    }

}

@Composable
fun FruitList() {
    val fruits = listOf("사과","바나나","키위","망고","수박")

    LazyColumn(contentPadding = PaddingValues(horizontal = 20.dp, vertical = 20.dp)
    ){
        items(fruits){ fruit ->
            FruitItem(name = fruit)
        }
    }
}

@Composable
fun FruitItem(name: String){
    Column(modifier = Modifier.padding(top = 10.dp))
    {
        Text(
            text = name
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    RecyclerViewTheme {
        Greeting("RecyclerView 테스트")
    }
}