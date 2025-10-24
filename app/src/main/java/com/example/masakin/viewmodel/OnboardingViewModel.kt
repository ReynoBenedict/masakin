package com.example.masakin.viewmodel

import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.example.masakin.R

data class OnboardingPage(
    @DrawableRes val image: Int,
    val title: String,
    val description: String
)

data class OnboardingUiState(
    val pages: List<OnboardingPage> = emptyList(),
    val currentPage: Int = 0
) {
    val totalPages: Int get() = pages.size
    val isLastPage: Boolean get() = currentPage >= totalPages - 1
    val current: OnboardingPage get() = pages[currentPage]
}

sealed interface OnboardingEffect {
    data object NavigateToAuth : OnboardingEffect // ke Login/Register
}

class OnboardingViewModel : ViewModel() {

    private val initialPages = listOf(
        OnboardingPage(
            image = R.drawable.gambar1,
            title = "Makan Sehat, Mulai Hari Ini",
            description = "Nikmati pengiriman bahan makanan segar langsung ke rumah Anda dengan MASAKIN."
        ),
        OnboardingPage(
            image = R.drawable.gambar2,
            title = "Dukung Lokal, Rasakan Bedanya",
            description = "Beli langsung dari produsen lokal segarkan dapur Anda dengan produk berkualitas."
        ),
        OnboardingPage(
            image = R.drawable.gambar3,
            title = "Masak Bersama Kami",
            description = "Masak cerdas dengan bantuan AI dan penuhi kebutuhan nutrisimu."
        )
    )

    private val _uiState = MutableStateFlow(OnboardingUiState(pages = initialPages))
    val uiState = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<OnboardingEffect>()
    val effect = _effect.asSharedFlow()

    fun next() {
        _uiState.update { s ->
            if (s.isLastPage) s else s.copy(currentPage = s.currentPage + 1)
        }
    }

    fun setPage(index: Int) {
        _uiState.update { s ->
            val idx = index.coerceIn(0, s.totalPages - 1)
            s.copy(currentPage = idx)
        }
    }

    suspend fun finish() {
        _effect.emit(OnboardingEffect.NavigateToAuth)
    }

    suspend fun skip() {
        _effect.emit(OnboardingEffect.NavigateToAuth)
    }
}
