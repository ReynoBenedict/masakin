package com.example.masakin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.masakin.data.model.Post
import com.example.masakin.data.repository.CommunityRepository
import com.example.masakin.data.repository.FirestoreCommunityRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CommunityUiState(
    val loading: Boolean = false,
    val selectedTab: Int = 0, // nol = recommended, satu = recent
    val recommended: List<Post> = emptyList(),
    val recent: List<Post> = emptyList(),
    val error: String? = null
)

class CommunityViewModel(
    private val repo: CommunityRepository = FirestoreCommunityRepository()
) : ViewModel() {

    private val _ui = MutableStateFlow(CommunityUiState(loading = true))
    val ui: StateFlow<CommunityUiState> = _ui

    init {
        refresh()
    }

    fun refresh() = viewModelScope.launch {
        _ui.update { it.copy(loading = true, error = null) }
        runCatching {
            val rec = repo.getRecommended()
            val recent = repo.getRecent()
            rec to recent
        }.onSuccess { (rec, recent) ->
            _ui.update { it.copy(loading = false, recommended = rec, recent = recent) }
        }.onFailure { e ->
            _ui.update { it.copy(loading = false, error = e.message ?: "Unknown error") }
        }
    }

    fun selectTab(index: Int) {
        _ui.update { it.copy(selectedTab = index) }
    }

    fun like(postId: String) {
        // optimistik update di UI
        _ui.update { state ->
            val list = currentList(state).map { p ->
                if (p.id == postId) p.copy(likes = p.likes + 1) else p
            }
            state.updateCurrentList(list)
        }

        // sync ke firestore
        viewModelScope.launch {
            runCatching { repo.likePost(postId) }
        }
    }

    // fungsi baru untuk membuat post
    fun createPost(
        userName: String,
        userHandle: String,
        userAvatarUrl: String,
        content: String,
        onFinish: (() -> Unit)? = null
    ) {
        viewModelScope.launch {
            _ui.update { it.copy(loading = true, error = null) }
            runCatching {
                repo.createPost(
                    userName = userName,
                    userHandle = userHandle,
                    userAvatarUrl = userAvatarUrl,
                    content = content
                    // imageUrl dibiarkan null, jadi post teks
                )
            }.onSuccess {
                refresh()
                onFinish?.invoke()
            }.onFailure { e ->
                _ui.update { it.copy(loading = false, error = e.message) }
            }
        }
    }

    private fun currentList(state: CommunityUiState) =
        if (state.selectedTab == 0) state.recommended else state.recent

    private fun CommunityUiState.updateCurrentList(list: List<Post>) =
        if (selectedTab == 0) copy(recommended = list) else copy(recent = list)
}
