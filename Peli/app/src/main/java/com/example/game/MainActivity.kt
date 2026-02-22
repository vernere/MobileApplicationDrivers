package com.example.game

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.game.ui.theme.GameTheme
import com.example.game.YliAliPeli

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GameTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Guess the number 1-10 ! (Made by Verner, 2400345)",
        modifier = modifier
    )
    GameTextField()
}

@Composable
fun GameTextField(modifier: Modifier = Modifier) {
    val startingNumber = remember { mutableIntStateOf((1..10).random()) }
    val textState = remember { mutableStateOf("") }
    val resultText = remember { mutableStateOf("") }

    Column(
        modifier = modifier.padding(50.dp)
    ) {
        TextField(
            value = textState.value,
            onValueChange = { textState.value = it },
            label = { Text("Enter your guess..") },
            placeholder = { Text("ex. 10") },
        )

        Button(
            onClick = {
                val guess = textState.value.toIntOrNull()
                if (guess != null) {
                    val yl = YliAliPeli()
                    resultText.value = yl.calculateGuess(startingNumber.intValue, guess)
                } else {
                    resultText.value = "Please enter a valid number"
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Play")
        }

        Button(
            onClick = {
                startingNumber.intValue = (1..10).random()
                resultText.value = ""
                textState.value = ""
            },
        ) {
            Text("Reset")
        }

        if (resultText.value.isNotEmpty()) {
            Text(
                text = resultText.value,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GameTheme {
        Greeting("Android")
    }
}

