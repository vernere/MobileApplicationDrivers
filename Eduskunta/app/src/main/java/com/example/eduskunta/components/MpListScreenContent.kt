package com.example.eduskunta.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.eduskunta.R
import com.example.eduskunta.local.MpEntity
import com.example.eduskunta.viewModel.GroupBy
import com.example.eduskunta.viewModel.MpListViewModel

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
@RequiresApi(Build.VERSION_CODES.O)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MpListScreenContent(
    groupedMps: Map<String, List<MpEntity>>,
    groupBy: GroupBy,
    onToggleGrouping: () -> Unit,
    onNavigateToDetail: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.text_title_kansanedustajat)) },
                actions = {
                    TextButton(onClick = onToggleGrouping) {
                        Text(
                            text =  if (groupBy == GroupBy.PARTY) stringResource(R.string.text_title_vaalipiiri) else stringResource(
                                R.string.text_title_puolue
                            ),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            groupedMps.forEach { (groupName, mps) ->
                item {
                    GroupHeader(groupName = groupName)
                }
                items(mps) { mp ->
                    MpRow(
                        mp = mp,
                        onClick = {onNavigateToDetail(mp.personNumber)}
                    )
                }
            }
        }
    }
}

@Composable
fun MpListScreen(
    viewModel: MpListViewModel,
    onNavigateToDetail: (Int) -> Unit
) {
    val groupedMps by viewModel.groupedMps.collectAsState()
    val groupBy by viewModel.groupBy.collectAsState()

    MpListScreenContent(
        groupedMps = groupedMps,
        groupBy = groupBy,
        onToggleGrouping = {viewModel.toggleGrouping()},
        onNavigateToDetail = onNavigateToDetail
    )
}