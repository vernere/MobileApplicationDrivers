package com.example.eduskunta.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.eduskunta.R
import com.example.eduskunta.local.MpEntity
import com.example.eduskunta.local.ReviewEntity
import com.example.eduskunta.utilities.MpImageUrl
import com.example.eduskunta.utilities.PartyNames
import com.example.eduskunta.utilities.calculateAge
import com.example.eduskunta.viewModel.MpDetailViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun MpDetailScreenContentPreview() {
    MpDetailScreenContent(
        mp = MpEntity(778, "Sari", "Essayah", "KD", "Savo-Karjala", "@sessayah", 1967, 14, picture = "attachment/member/pictures/Essayah-Sari-web-v8260-778.JPG"),
        reviews = listOf(
            ReviewEntity(1, 778, true, "Hyvä puhe!", "2.2.2026"),
            ReviewEntity(2, 778, false, "En ole samaa mieltä.", "15.2.2026")
        ),
        onNavigateBack = {},
        onAddReview = { _, _ -> }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MpDetailScreenContent(
    mp: MpEntity,
    reviews: List<ReviewEntity>,
    onNavigateBack: () -> Unit,
    onAddReview: (isPositive: Boolean, text: String) -> Unit
) {
    val uriHandler = LocalUriHandler.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("${mp.firstName} ${mp.lastName}") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "takaisin")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            item {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(MpImageUrl.getUrl(mp.firstName, mp.lastName, mp.personNumber))
                        .crossfade(true)
                        .build(),
                    contentDescription = "${mp.firstName} ${mp.lastName}",
                    placeholder = painterResource(R.drawable.placeholder),
                    error = painterResource(R.drawable.placeholder),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                )
            }

            item {
                Column(modifier = Modifier.padding((16.dp))) {
                    Text(
                        text = "${mp.firstName} ${mp.lastName}",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Puolue: ${PartyNames.getFullName(mp.party)}")
                    Text("Vaalipiiri: ${mp.constituency}")
                    Text("Ikä: ${calculateAge(mp.bornYear)} vuotta")

                    mp.twitter?.let { handle ->
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = handle,
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.clickable {
                                val url = "https://twitter.com/\${handle.removePrefix(\"@\")}"
                                uriHandler.openUri(url)
                            }
                        )
                    }
                }
                HorizontalDivider()
            }
            item {
                AddReviewSection(onSubmit = onAddReview)
                HorizontalDivider()
            }
            item {
                Text(
                    text = "Arviot (${reviews.size})",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
            if (reviews.isEmpty()) {
                item {
                    Text(
                        text = stringResource(R.string.text_label_ei_arvioita),
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                items(reviews) { review ->
                    ReviewCard(review = review)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MpDetailScreen(
    viewModel: MpDetailViewModel,
    onNavigateBack: () -> Unit
) {
    val mp by viewModel.mp.collectAsState()
    val reviews by viewModel.reviews.collectAsState()

    mp?.let { safeMp ->
        MpDetailScreenContent(
            mp = safeMp,
            reviews = reviews,
            onNavigateBack = onNavigateBack,
            onAddReview = { isPositive, text ->
                viewModel.addReview(isPositive, text)
            }
        )
    } ?: Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}
