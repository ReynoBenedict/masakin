package com.example.masakin.ui.recipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.masakin.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecipeViewModel : ViewModel() {

    private val _ui = MutableStateFlow(RecipeUiState())
    val ui: StateFlow<RecipeUiState> = _ui

    // Build dummy data once and reuse
    private val allRecipes: List<Recipe> by lazy { buildDummyRecipes() }

    init {
        // Simulasi fetch data
        viewModelScope.launch {
            delay(400L)
            _ui.value = RecipeUiState(
                isLoading = false,
                featured = allRecipes.take(5),
                categories = allRecipes.groupBy { it.category }
            )
        }
    }

    fun updateQuery(q: String) {
        _ui.value = _ui.value.copy(query = q)
    }

    // GET RECIPE BY ID (menggunakan cached list)
    fun getRecipeById(id: String): Recipe? {
        return allRecipes.find { it.id == id }
    }

    // ----- Dummy data (pakai drawable) -----
    private fun buildDummyRecipes(): List<Recipe> = listOf(
        // ===== Mie (4) =====
        Recipe("r1","Mie Ayam Indonesia",15,4.6, R.drawable.mie_ayam, "Mie"),
        Recipe("r6","Mie Goreng Jawa Pedas",20,4.7, R.drawable.mie_goreng, "Mie"),
        Recipe("r9","Mie Kuah Bakso",18,4.4, R.drawable.mie_kuah, "Mie"),
        Recipe("r10","Mie Tek-tek Gerobak",22,4.5, R.drawable.mie_tektek, "Mie"),

        // ===== Nasi (4) =====
        Recipe("r2","Nasi Goreng Rumahan",12,4.5, R.drawable.nasi_goreng, "Nasi"),
        Recipe("r3","Nasi Uduk Ayam Suwir",40,4.3, R.drawable.nasi_uduk, "Nasi"),
        Recipe("r7","Nasi Kuning Komplit",60,4.6, R.drawable.nasi_kuning, "Nasi"),
        Recipe("r8","Nasi Liwet Ikan Teri",45,4.4, R.drawable.nasi_liwet, "Nasi"),

        // ===== Snack (4) =====
        Recipe("r4","Perkedel Kentang Crispy",30,4.4, R.drawable.perkedel, "Snack"),
        Recipe("r5","Putu Ayu Lembut",35,4.2, R.drawable.putu_ayu, "Snack"),
        Recipe("r11","Pisang Goreng Madu",25,4.5, R.drawable.pisang_goreng, "Snack"),
        Recipe("r12","Cilok Bumbu Kacang",28,4.3, R.drawable.cilok, "Snack")
    )
}
