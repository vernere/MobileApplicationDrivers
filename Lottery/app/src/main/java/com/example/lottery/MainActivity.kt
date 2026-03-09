package com.example.lottery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {

    private val viewModel: LotteryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val state by viewModel.state.collectAsState()
            LotteryScreen(
                state = state,
                onNumberClick = viewModel::toggleNumber,
                onPlay = viewModel::play,
                onReset = viewModel::reset
            )
        }
    }
}


@Composable
fun LotteryScreen(
    state: LotteryState,
    onNumberClick: (Int) -> Unit,
    onPlay: () -> Unit,
    onReset: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Lottery",
            fontSize = 26.sp,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Pick 7 numbers (${state.selectedNumbers.size}/7)",
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(8),
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items((1..40).toList()) { number ->
                val isSelected = state.selectedNumbers.contains(number)
                val isWinner = state.drawnNumbers.contains(number)

                val ballColor = when {
                    isWinner -> Color(0xFF4CAF50)
                    isSelected -> Color(0xFFF5A623)
                    else -> Color(0xFF3A3A5C)
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .clip(CircleShape)
                        .background(ballColor)
                        .clickable { onNumberClick(number) }
                ) {
                    Text(
                        text = number.toString(),
                        color = Color.White,
                        fontSize = 13.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onPlay,
            enabled = state.selectedNumbers.size == 7,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF5A623)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Play",
                fontSize = 18.sp
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = onReset,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White)
        ) {
            Text(text = "Reset", fontSize = 16.sp)
        }

        if (state.hasPlayed) {
            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .padding(16.dp),
                horizontalAlignment =  Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Drawn: ${state.drawnNumbers.joinToString("  .  ")}",
                    color = Color.White,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(6.dp))

                val message = when (state.matchCount) {
                    7 -> "All 7 matched"
                    6 -> "All 6 matched"
                    5 -> "All 5 matched"
                    4 -> "All 4 matched"
                    3 -> "All 3 matched"
                    else -> "${state.matchCount} matches, Try again"
                }

                Text(
                    text = message,
                    fontSize = 20.sp
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun LotteryScreenPreview() {
    LotteryScreen(
        state = LotteryState(
            selectedNumbers = setOf(3,7,12,19,25,33,40),
            drawnNumbers = listOf(3,7,12,19,25,33,40),
            matchCount = 7,
            hasPlayed = true
        ),
        onNumberClick = {},
        onPlay = {},
        onReset = {}
    )
}


