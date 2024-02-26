package com.example.moduledivide.ui.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.CharacterData
import com.example.moduledivide.R
import com.example.moduledivide.ui.theme.ModuleDivideTheme
import com.example.moduledivide.ui.vm.CharacterViewModel


@Composable
fun DetailScreen(viewModel: CharacterViewModel) {
    Column(
        modifier = Modifier.padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current

        Image(
            painterResource(id = viewModel.getSelectedCharacter().value!!.image),
            contentDescription = stringResource(id = viewModel.getSelectedCharacter().value!!.title),
            modifier = Modifier
                .width(250.dp)
                .height(250.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(id = viewModel.getSelectedCharacter().value!!.title),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 70.dp),
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = stringResource(id = viewModel.getSelectedCharacter().value!!.describe),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 70.dp),
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "YouTube Link",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 70.dp),
            fontSize = 18.sp
        )

        Text(
            text = stringResource(id = viewModel.getSelectedCharacter().value!!.youtubeLink),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 70.dp, end = 70.dp)
                .clickable {
                    val url =
                        context.getString(viewModel.getSelectedCharacter().value!!.youtubeLink)
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    context.startActivity(intent)
                },
            fontSize = 16.sp,
            color = Color.Blue
        )

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DetailPreview() {
    ModuleDivideTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val character = CharacterData(
                title = R.string.baknana,
                describe = R.string.bak_describe,
                image = R.drawable.baknana,
                youtubeLink = R.string.baknana_link
            )
            DetailScreen(CharacterViewModel(character))
        }
    }
}
