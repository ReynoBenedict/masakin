package com.example.masakin.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.masakin.ui.components.BottomNavBar

@Composable
fun ChatScreen(
    onOpenHome: () -> Unit,
    onOpenChatbot: () -> Unit = {},
    onOpenMyFood: () -> Unit = {},
    onOpenProfile: () -> Unit = {}
) {
    // posisi awal tab di "chat"
    var selectedTab by remember { mutableStateOf("chat") }

    // handle pindah halaman dari navbar
    LaunchedEffect(selectedTab) {
        when (selectedTab) {
            "home" -> onOpenHome()
            "chat" -> {}              // sudah di halaman chat
            "Chatbot" -> onOpenChatbot()
            "food" -> onOpenMyFood()
            "profile" -> onOpenProfile()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        // Konten utama halaman chat
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 90.dp),   // kasih ruang buat navbar
            contentAlignment = Alignment.Center
        ) {
            Text("Halaman Chat", modifier = Modifier.padding(20.dp))
        }

        // Navbar di bawah
        BottomNavBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            selectedItem = selectedTab,
            onItemSelected = { selectedTab = it }
        )
    }
}
