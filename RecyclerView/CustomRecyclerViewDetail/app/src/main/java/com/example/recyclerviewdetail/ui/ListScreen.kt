package com.example.recyclerviewdetail.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.recyclerviewdetail.data.Character
import com.example.recyclerviewdetail.data.CharacterProvider
import com.example.recyclerviewdetail.ui.theme.RecyclerViewDetailTheme
import com.example.recyclerviewdetail.vm.CharacterViewModel


@Composable
fun ListScreen(navController: NavHostController, characterViewModel : CharacterViewModel, modifier: Modifier = Modifier) {

    Column(
        modifier = modifier.padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "RecyclerViewDetail",
            modifier = modifier
        )

        Column(
            modifier = modifier.padding(top = 20.dp)
        ) {
            CharacterList(navController,characterViewModel)
        }
    }
}

@Composable
fun CharacterList(
    navController: NavHostController = rememberNavController(),
    characterViewModel: CharacterViewModel
) {
    val characterState = CharacterProvider().getCharacterList()

    LazyColumn(
        modifier = Modifier
            .fillMaxHeight(),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 20.dp)
    )
    {
        items(characterState.value) { character ->
            CharacterItem(character){
                characterViewModel.setSelectedCharacter(it)
                navController.navigate("DetailScreen")
            }
        }
    }


}

@Composable
fun CharacterItem(character: Character, onItemClick: (Character) -> Unit){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color(0xFFBABABA))
            .clickable { onItemClick(character) }
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
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ListScreen(rememberNavController(),CharacterViewModel())
        }
    }
}