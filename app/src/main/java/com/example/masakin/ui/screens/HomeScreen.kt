package com.example.masakin.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onOpenChatbot: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(Modifier.height(12.dp))
        FilledTonalButton(
            onClick = onOpenChatbot,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
        ) {
            Text("Buka ChatBot", style = MaterialTheme.typography.bodySmall)
        }
    }
}
