package com.example.antonioapp

import android.media.SoundPool
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.border
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.antonioapp.ui.theme.AntonioappTheme

class MainActivity : ComponentActivity() {

    private lateinit var soundPool: SoundPool

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize SoundPool
        soundPool = SoundPool.Builder()
            .setMaxStreams(9) // Allow up to 9 sounds to play concurrently
            .build()

        // Load sound files (replace with your actual sound file resources)
        val soundIds = listOf(
            soundPool.load(this, R.raw.one, 1),
            soundPool.load(this, R.raw.two, 1),
            soundPool.load(this, R.raw.three, 1),
            soundPool.load(this, R.raw.four, 1),
            soundPool.load(this, R.raw.five, 1),
            soundPool.load(this, R.raw.six, 1),
            soundPool.load(this, R.raw.seven, 1),
            soundPool.load(this, R.raw.eight, 1),
            soundPool.load(this, R.raw.nine, 1)
        )

        setContent {
            AntonioappTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SoundGrid(
                        soundIds = soundIds,
                        soundPool = soundPool,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        soundPool.release()
    }
}

@Composable
fun SoundGrid(
    soundIds: List<Int>,
    soundPool: SoundPool,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        repeat(3) { row ->
            Row(
                modifier = Modifier.fillMaxWidth(), // Add this line
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                repeat(3) { col ->
                    val index = row * 3 + col
                    SoundButton(
                        soundId = soundIds[index],
                        soundPool = soundPool,
                        modifier = Modifier
                            .size(80.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun SoundButton(
    soundId: Int,
    soundPool: SoundPool,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clickable {
                soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
            }
            .border(1.dp, MaterialTheme.colorScheme.onSurface)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_sound), // Replace with your icon
            contentDescription = "Sound Button",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SoundGridPreview() {
    // Provide dummy sound IDs for preview
    val dummySoundIds = List(9) { 0 }
    AntonioappTheme {
        SoundGrid(soundIds = dummySoundIds, soundPool = SoundPool.Builder().build())
    }
}