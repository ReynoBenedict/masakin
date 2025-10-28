package com.example.masakin.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
@Preview(showBackground = true)
@Composable
fun HomeScreen(
    onOpenChatbot: () -> Unit = {},
    onOpenRecipe: () -> Unit = {},
    onOpenCommunity: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        FilledTonalButton(
            onClick = onOpenChatbot,
            modifier = Modifier.fillMaxWidth().height(52.dp)
        ) {
            Text("Buka ChatBot", style = MaterialTheme.typography.bodySmall)
        }

        Spacer(Modifier.height(16.dp))

        FilledTonalButton(
            onClick = onOpenRecipe,
            modifier = Modifier.fillMaxWidth().height(52.dp)
        ) {
            Text("Buka Halaman Resep", style = MaterialTheme.typography.bodySmall)
        }

        Spacer(Modifier.height(16.dp))

        FilledTonalButton(
            onClick = onOpenCommunity, // <- fixed
            modifier = Modifier.fillMaxWidth().height(52.dp)
        ) {
            Text("Buka Halaman Komunitas", style = MaterialTheme.typography.bodySmall)
        }
    }
}
