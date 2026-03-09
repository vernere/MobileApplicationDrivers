package com.example.eduskunta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.eduskunta.components.MpListScreenContent
import com.example.eduskunta.local.AppDatabase
import com.example.eduskunta.local.MpEntity
import com.example.eduskunta.navigation.AppNavigation
import com.example.eduskunta.ui.theme.EduskuntaTheme
import com.example.eduskunta.viewModel.GroupBy

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = AppDatabase.getDatabase(this)

        setContent {
            EduskuntaTheme {
                AppNavigation(database = database)
            }
        }
    }
}

private val previewMps = listOf(
    MpEntity(1, "Petteri", "Orpo", "Kokoomus", "Uusimaa", null, "1969", null),
    MpEntity(2, "Sari", "Essayah", "KD", "Savo-Karjala", "@sessayah", "1967", null),
    MpEntity(3, "Li", "Andersson", "Vas", "Varsinais-Suomi", "@liandersson", "1987", null),
)

private val previewGrouped = mapOf(
    "Kokoomus" to listOf(previewMps[0]),
    "KD" to listOf(previewMps[1]),
    "Vas" to listOf(previewMps[2])
)

@Preview(showBackground = true)
@Composable
fun MpListScreenPreview() {
    MpListScreenContent(
        groupedMps = previewGrouped,
        groupBy = GroupBy.PARTY,
        onToggleGrouping = {},
        onNavigateToDetail = {}
    )
}