package com.example.staggeedgriddetail.ui.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.staggeedgriddetail.data.Character
import com.example.staggeedgriddetail.data.CharacterProvider
import com.example.staggeedgriddetail.ui.theme.StaggeedGridDetailTheme
import com.example.staggeedgriddetail.ui.vm.CharacterViewModel

@Composable
fun GridScreen(navController: NavController, characterViewModel: CharacterViewModel, modifier: Modifier = Modifier) {

    val characterState = CharacterProvider().getCharacterList()

    LazyVerticalStaggeredGrid(
        state = rememberLazyStaggeredGridState(),
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(10.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ){
        itemsIndexed(
            items = characterState.value,
            key = { _ : Int, item : Character ->
                item.hashCode()
            }
        ) { _, item ->
            ItemView(itemData = item) {
                characterViewModel.setSelectedCharacter(it)
                navController.navigate("DetailScreen")
            }
        }
    }

}

@Composable
fun ItemView(itemData: Character, onItemClick: (Character) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(itemData.height.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable { onItemClick(itemData) }
        ,
        contentAlignment = Alignment.Center
    ) {
        Image(painter = painterResource(id = itemData.image),
            contentDescription = stringResource(id = itemData.title)
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GridPreView() {

    StaggeedGridDetailTheme {
        GridScreen(rememberNavController(), CharacterViewModel())
    }
}
