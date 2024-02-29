package com.example.volumncontrol

import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.volumncontrol.system.mediaSoundUp
import com.example.volumncontrol.system.notiSoundUp
import com.example.volumncontrol.system.ringSoundUp
import com.example.volumncontrol.system.systemSoundUp
import com.example.volumncontrol.ui.theme.VolumnControlTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        setContent {
            VolumnControlTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(audioManager)
                }
            }
        }
    }
}

@Composable
fun Greeting(audioManager: AudioManager) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Button(onClick = { systemSoundUp(audioManager) },
            modifier = Modifier
                .padding(top = 100.dp)
                .size(width = 200.dp, height = 40.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF56E1F3),
                contentColor = Color.Black)

        ) {
            Text(text = "시스템 음향 키우기")
        }

        Button(onClick = { mediaSoundUp(audioManager) },
            modifier = Modifier
                .padding(top = 100.dp)
                .size(width = 200.dp, height = 40.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF56E1F3),
                contentColor = Color.Black)

        ) {
            Text(text = "미디어 음향 키우기")
        }

        Button(onClick = { notiSoundUp(audioManager) },
            modifier = Modifier
                .padding(top = 100.dp)
                .size(width = 200.dp, height = 40.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF56E1F3),
                contentColor = Color.Black)

        ) {
            Text(text = "알림 음향 키우기")
        }

        Button(onClick = { ringSoundUp(audioManager) },
            modifier = Modifier
                .padding(top = 100.dp)
                .size(width = 200.dp, height = 40.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF56E1F3),
                contentColor = Color.Black)

        ) {
            Text(text = "벨소리 음향 키우기")
        }

    }

}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VolumnControlTheme {
        val context = LocalContext.current
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        Greeting(audioManager)
    }
}