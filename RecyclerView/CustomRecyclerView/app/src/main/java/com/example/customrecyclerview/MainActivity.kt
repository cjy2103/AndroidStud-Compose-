package com.example.customrecyclerview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.customrecyclerview.data.Character
import com.example.customrecyclerview.data.CharacterProvider
import com.example.customrecyclerview.ui.theme.CustomRecyclerViewTheme
import java.math.BigDecimal

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CustomRecyclerViewTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("RecyclerView")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "$name",
            modifier = modifier
        )
    }

    Column(
        modifier = Modifier.padding(top = 60.dp)
    ) {
        CharacterList()
    }


}

@Composable
fun CharacterList(){
    val characterState = CharacterProvider().getCharacterList()

    LazyColumn(
        modifier = Modifier
            .fillMaxHeight(),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 20.dp)
    )
    {
        items(characterState.value) { character ->
            CharacterItem(character)
        }
    }


}

@Composable
fun CharacterItem(character: Character){

        Row(
            modifier = Modifier.fillMaxWidth()
                .border(
                    brush = Brush.horizontalGradient(listOf(Color.Green, Color.Blue)),
                    width = 1.dp,
                    shape = RectangleShape
                )
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
//    Row(
//        modifier = Modifier.fillMaxWidth()
//    ){
//        Image(painter = painterResource(id = character.image),
//            contentDescription = stringResource(id = character.title),
//            modifier = Modifier
//                .width(100.dp)
//                .height(100.dp)
//        )
//
//        Column(modifier = Modifier
//            .fillMaxWidth()
//            .padding(top = 20.dp, start = 20.dp, end = 20.dp)){
//            Text(
//                text = stringResource(id = character.title),
//                fontSize = 24.sp
//            )
//            Text(
//                text = stringResource(id = character.describe),
//                fontSize = 18.sp
//            )
//        }
//
//    }

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    CustomRecyclerViewTheme {
        Greeting("RecyclerView")
    }
}