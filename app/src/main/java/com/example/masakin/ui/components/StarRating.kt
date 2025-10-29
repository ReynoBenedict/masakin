package com.example.masakin.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Row
import kotlin.math.roundToInt

@Composable
fun StarRating(rating: Double, modifier: Modifier = Modifier) {
    val filled = rating.coerceIn(0.0, 5.0).roundToInt()
    Row(modifier) {
        repeat(5) { i ->
            Icon(
                imageVector = if (i < filled) Icons.Filled.Star else Icons.Outlined.Star,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}
