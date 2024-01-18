package com.example.recyclerviewdetail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.recyclerviewdetail.data.Character
import com.example.recyclerviewdetail.ui.DetailScreen
import com.example.recyclerviewdetail.ui.ListScreen
import com.example.recyclerviewdetail.ui.theme.RecyclerViewDetailTheme
import com.example.recyclerviewdetail.vm.CharacterViewModel

class MainActivity : ComponentActivity() {
    private val characterViewModel : CharacterViewModel by lazy {
        ViewModelProvider(this)[CharacterViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RecyclerViewDetailTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("RecyclerViewDetail",characterViewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, characterViewModel: CharacterViewModel, modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NavHost(
            navController = navController,
            startDestination = "ListScreen"
        ){
            composable("ListScreen"){
                ListScreen(navController,characterViewModel)
            }
            composable("DetailScreen"){
                DetailScreen(navController,characterViewModel)
            }
        }
    }

}

@Composable
fun CharacterItem(character: Character){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color(0xFFBABABA))
    ){
        Image(painter = painterResource(id = character.image),
            contentDescription = stringResource(id = character.title),
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
                .border(1.dp, Color(0xFFBABABA))
        )

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 20.dp, end = 20.dp)){
            Text(
                text = stringResource(id = character.title),
                fontSize = 24.sp
            )
            Text(
                text = stringResource(id = character.describe),
                fontSize = 18.sp
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    RecyclerViewDetailTheme {
        Greeting("RecyclerViewDetail", CharacterViewModel())
    }
}