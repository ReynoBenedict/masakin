package com.example.masakin.ui.community

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.masakin.ui.screens.CreatePostScreen
import com.example.masakin.viewmodel.CommunityViewModel

@Composable
fun CreatePostRoute(
    onBack: () -> Unit,
    userName: String = "Ahmad Syahroni",
    userHandle: String = "syahroni",
    userAvatarUrl: String = "https://img.okezone.com/content/2024/09/08/338/3060155/ahmad_syahroni-AY1I_large.jpg",
    viewModel: CommunityViewModel = viewModel()
) {
    val ui by viewModel.ui.collectAsStateWithLifecycle()

    CreatePostScreen(
        userName = userName,
        userHandle = userHandle,
        userAvatarUrl = userAvatarUrl,
        loading = ui.loading,
        error = ui.error,
        onBack = onBack,
        onPost = { content ->
            if (content.isNotBlank()) {
                viewModel.createPost(
                    userName = userName,
                    userHandle = userHandle,
                    userAvatarUrl = userAvatarUrl,
                    content = content
                ) {
                    // setelah sukses, balik ke layar sebelumnya
                    onBack()
                }
            }
        }
    )
}
