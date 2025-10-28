package com.example.masakin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.masakin.data.model.Post
import com.example.masakin.data.repository.CommunityRepository
import com.example.masakin.data.repository.FakeCommunityRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CommunityUiState(
    val loading: Boolean = false,
    val selectedTab: Int = 0, // 0=recommended, 1=recent
    val recommended: List<Post> = emptyList(),
    val recent: List<Post> = emptyList(),
    val error: String? = null
)

class CommunityViewModel(
    private val repo: CommunityRepository = FakeCommunityRepository()
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
        _ui.update {
            val list = currentList(it).map { p -> if (p.id == postId) p.copy(likes = p.likes + 1) else p }
            it.updateCurrentList(list)
        }
    }

    private fun currentList(state: CommunityUiState) =
        if (state.selectedTab == 0) state.recommended else state.recent

    private fun CommunityUiState.updateCurrentList(list: List<Post>) =
        if (selectedTab == 0) copy(recommended = list) else copy(recent = list)
}
