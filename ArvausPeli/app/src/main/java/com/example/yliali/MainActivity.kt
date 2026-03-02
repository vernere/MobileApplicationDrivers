package com.example.yliali

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.yliali.ui.theme.YliAliTheme
import com.example.yliali.YliAliViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: YliAliViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val state by viewModel.state.collectAsState()
            YliAliPeli(
                state = state,
                onStart = viewModel::start,
                onPlay = viewModel::guess,
                onReset = viewModel::reset
            )
        }
    }
}

@Composable
fun YliAliPeli(
    modifier: Modifier = Modifier,
    state: GuessingState,
    onStart: () -> Unit,
    onPlay: (Int?) -> Unit,
    onReset: () -> Unit

) {
    val textState = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        onStart()
    }
    Column(
        modifier = modifier.padding(50.dp)
    ) {
        Text(stringResource(R.string.text_title), modifier = modifier.padding(10.dp))

        TextField(
            value = textState.value,
            onValueChange = { textState.value = it },
            label = { Text(stringResource(R.string.text_label)) },
            placeholder = { Text(stringResource(R.string.text_placeholder)) },
            modifier = modifier.padding(5.dp)
        )

        Button(
            onClick = { onPlay(textState.value.toIntOrNull()) }
        ) { Text(stringResource(R.string.button_play)) }

        Button(
            onClick = onReset
        ) { Text(stringResource(R.string.button_reset)) }

        val feedbackText = when (state.result) {
            GuessResult.CORRECT -> stringResource(R.string.text_correct)
            GuessResult.TOO_HIGH -> stringResource(R.string.text_too_high)
            GuessResult.TOO_LOW -> stringResource(R.string.text_too_low)
            GuessResult.NONE -> ""
        }

        if (feedbackText.isNotEmpty()) {
            Text(feedbackText, modifier = modifier.padding(10.dp))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun YluAliPeliPreview() {
    YliAliPeli(
        state = GuessingState(
            generatedNumber = 10,
            guess = 2,
            result = GuessResult.TOO_LOW
        ),
        onStart = {},
        onPlay = {},
        onReset = {}
    )
}


