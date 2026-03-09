package com.example.retrofit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.retrofit.ui.theme.RetrofitTheme
import net.homeip.kotilabra.retrofitcompose.DataProvider
import net.homeip.kotilabra.retrofitcompose.President

class MainActivity : ComponentActivity() {
    val db = DataProvider

    private val viewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PresidentList(db.presidents, viewModel)
        }
    }
}

@Composable
fun PresidentList(presidents: MutableList<President>, viewModel: MyViewModel) {
    Column {
        Text(
            text = "Hits: ${viewModel.wikiUiState.value}",
            modifier = Modifier.padding(top = 20.dp)
        )

        Spacer(modifier = Modifier.padding(10.dp))

        presidents.forEach { president ->
            PresidentRow(president, viewModel)
        }
    }
}

@Composable
private fun PresidentRow(president: President, viewModel: MyViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
    ) {
        Column {
            Text(
                text = president.name,
                modifier = Modifier.clickable { viewModel.getHits(president.name) },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PresidentListPreview() {
    RetrofitTheme() {
        val db = DataProvider
        PresidentList(db.presidents, viewModel = MyViewModel())
    }
}

