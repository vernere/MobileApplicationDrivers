package com.example.eduskunta.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.eduskunta.R
import com.example.eduskunta.local.MpEntity
import com.example.eduskunta.utilities.MpImageUrl

@Preview(showBackground = true)
@Composable
fun MpRowPreview() {
    MpRow(
        mp = MpEntity(1, "Petteri", "Orpo", "Kokoomus", "Uusimaa", null, 1969, null, picture = "attachment/member/pictures/Essayah-Sari-web-v8260-778.JPG"),
        onClick = {}
    )
}

@Composable
fun MpRow(
    mp: MpEntity,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(MpImageUrl.getUrl(mp.firstName, mp.lastName, mp.personNumber))
                .crossfade(true)
                .build(),
            contentDescription = "${mp.firstName}, ${mp.lastName}",
            placeholder = painterResource(R.drawable.placeholder),
            error = painterResource(R.drawable.placeholder),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(12.dp  ))

        Column {
            Text(
                text = "${mp.firstName} ${mp.lastName}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = mp.constituency,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        HorizontalDivider()
    }
}