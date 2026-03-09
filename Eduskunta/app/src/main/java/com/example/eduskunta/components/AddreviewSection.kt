package com.example.eduskunta.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.eduskunta.R

@Preview(showBackground = true)
@Composable
fun AddReviewSectionPreview() {
    AddReviewSection(onSubmit = { _, _ -> })
}

@Composable
fun AddReviewSection(
    onSubmit: (isPositive: Boolean, text: String) -> Unit
) {

    var isPositive by remember { mutableStateOf(true) }
    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(stringResource(R.string.text_title_arvo), style = MaterialTheme.typography.titleSmall)

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            FilterChip(
                selected = isPositive,
                onClick = { isPositive = true },
                label = { Text(stringResource(R.string.text_label_positiivinen)) },
                modifier = Modifier.padding(end = 8.dp)
            )
            FilterChip(
                selected = !isPositive,
                onClick = { isPositive = false },
                label = { Text(stringResource(R.string.text_label_negatiivinen)) }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = text,
            onValueChange = {text = it},
            label = {Text(stringResource(R.string.text_label_placeholder))},
            modifier = Modifier.fillMaxWidth(),
            minLines = 2
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (text.isNotBlank()) {
                    onSubmit(isPositive, text)
                    text = ""
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(stringResource(R.string.text_label_lähetä))
        }
    }
}