package com.example.customrecyclerview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
        modifier = Modifier.fillMaxHeight(),
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
    ){
        Image(painter = painterResource(id = character.image),
            contentDescription = stringResource(id = character.title),
            modifier = Modifier
                .width(150.dp)
                .height(150.dp)
        )
        Text(text = stringResource(id = character.title))
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = stringResource(id = character.describe))
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    CustomRecyclerViewTheme {
        Greeting("RecyclerView")
    }
}