package com.example.eduskunta.components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.eduskunta.local.ReviewEntity

@Preview(showBackground = true)
@Composable
fun ReviewCardPreview() {
    ReviewCard(
        review = ReviewEntity(
            id = 1,
            mpPersonNumber = 1,
            isPositive = true,
            text = "Hyvä puhe eduskunnassa!",
            date = "2.2.2026"
        )
    )
}

@Composable
fun ReviewCard(review: ReviewEntity){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp ),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = if(review.isPositive)
                Icons.Default.ThumbUp else Icons.Default.Warning,
            contentDescription = null,
            tint = if (review.isPositive)
                Color(0xFF4CAF50) else Color(0xFFF44336),
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = review.date,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = review.text,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
    HorizontalDivider()
}