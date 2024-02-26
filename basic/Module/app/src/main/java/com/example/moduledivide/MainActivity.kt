package com.example.moduledivide

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.moduledivide.ui.navigation.NavigationHost
import com.example.moduledivide.ui.theme.ModuleDivideTheme
import com.example.moduledivide.ui.vm.CharacterViewModel

class MainActivity : ComponentActivity() {

    private val characterViewModel : CharacterViewModel by lazy {
        ViewModelProvider(this)[CharacterViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ModuleDivideTheme {
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
fun Greeting(viewModel: CharacterViewModel, modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavigationHost(navController = navController, viewModel = viewModel)

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ModuleDivideTheme {
        Greeting(CharacterViewModel())
    }
}