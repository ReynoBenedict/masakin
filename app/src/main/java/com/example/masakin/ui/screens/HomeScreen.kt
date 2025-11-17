package com.example.masakin.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.masakin.ui.components.BottomNavBar

@Composable
fun HomeScreen(
    onOpenChatbot: () -> Unit = {},
    onOpenRecipe: () -> Unit = {},
    onOpenCommunity: () -> Unit = {},
    onOpenMart: () -> Unit = {},
    onOpenConsultation: () -> Unit = {},
    onOpenProfile: () -> Unit,
    onOpenMyFood: () -> Unit,
    onOpenChat: () -> Unit
) {
    var selectedTab by remember { mutableStateOf("home") }

    // ========== HANDLE NAVIGASI DARI NAVBAR ==========
    LaunchedEffect(selectedTab) {
        when (selectedTab) {
            "home" -> {}
            "chat" -> onOpenChat()
            "Chatbot" -> onOpenChatbot()
            "food" -> onOpenMyFood()
            "profile" -> onOpenProfile()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        // ==== KONTEN HALAMAN ====
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .padding(bottom = 90.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            FilledTonalButton(
                onClick = onOpenRecipe,
                modifier = Modifier.fillMaxWidth().height(52.dp)
            ) { Text("Buka Halaman Resep") }

            Spacer(Modifier.height(16.dp))

            FilledTonalButton(
                onClick = onOpenCommunity,
                modifier = Modifier.fillMaxWidth().height(52.dp)
            ) { Text("Buka Halaman Komunitas") }

            Spacer(Modifier.height(16.dp))

            FilledTonalButton(
                onClick = onOpenMart,
                modifier = Modifier.fillMaxWidth().height(52.dp)
            ) { Text("Buka Halaman Mart") }

            Spacer(Modifier.height(16.dp))

            FilledTonalButton(
                onClick = onOpenConsultation,
                modifier = Modifier.fillMaxWidth().height(52.dp)
            ) { Text("Buka Halaman Konsultasi") }
        }

        // ==== NAVBAR MENEMPEL DI BAWAH ====
        BottomNavBar(
            modifier = Modifier.align(Alignment.BottomEnd),
            selectedItem = selectedTab,
            onItemSelected = { selectedTab = it }
        )
    }
}
