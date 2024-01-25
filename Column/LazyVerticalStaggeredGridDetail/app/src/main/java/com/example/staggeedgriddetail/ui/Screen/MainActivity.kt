package com.example.staggeedgriddetail.ui.Screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.staggeedgriddetail.data.Character
import com.example.staggeedgriddetail.ui.theme.StaggeedGridDetailTheme
import com.example.staggeedgriddetail.ui.vm.CharacterViewModel

class MainActivity : ComponentActivity() {

    private val characterViewModel : CharacterViewModel by lazy {
        ViewModelProvider(this)[CharacterViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StaggeedGridDetailTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(characterViewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting(characterViewModel: CharacterViewModel) {

    val navController = rememberNavController()

    Column(
        modifier = Modifier.fillMaxSize()
    )
    {
         NavHost(navController = navController,
                 startDestination = "GridScreen")
         {
             composable("GridScreen"){
                 GridScreen(navController, characterViewModel)
             }
             composable("DetailScreen"){
                 DetailScreen(characterViewModel)
             }
         }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    StaggeedGridDetailTheme {
        Greeting(CharacterViewModel())
    }
}