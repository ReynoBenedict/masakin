package com.example.masakin.ui.community // atau package-mu saat ini

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.masakin.ui.screens.CommunityScreen
import com.example.masakin.viewmodel.CommunityViewModel

@Composable
fun CommunityRoute(
    onBack: () -> Unit = {},
    onCreatePost: () -> Unit = {},
    viewModel: CommunityViewModel = viewModel()
) {
    // load sekali saat layar dibuka
    LaunchedEffect(Unit) { viewModel.refresh() }

    // ambil UiState dari VM
    val ui by viewModel.ui.collectAsStateWithLifecycle()

    // teruskan ke screen stateless
    CommunityScreen(
        ui = ui,
        onBack = onBack,
        onTabClick = viewModel::selectTab,
        onCreatePost = onCreatePost,
        onLike = { id -> viewModel.like(id) },
        onComment = { /* TODO */ },
        onShare = { /* TODO */ },
        onSave = { /* TODO */ },
        profileImage = "https://img.okezone.com/content/2024/09/08/338/3060155/ahmad_syahroni-AY1I_large.jpg" // atau viewModel.profileImageUrl kalau ada
    )
}
