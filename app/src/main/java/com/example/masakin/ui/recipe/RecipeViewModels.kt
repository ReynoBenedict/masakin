package com.example.masakin.ui.recipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecipeViewModel : ViewModel() {

    private val _ui = MutableStateFlow(RecipeUiState())
    val ui: StateFlow<RecipeUiState> = _ui

    init {
        // Simulasi fetch data
        viewModelScope.launch {
            delay(400)
            val all = dummyRecipes()
            _ui.value = RecipeUiState(
                isLoading = false,
                featured = all.take(5),
                categories = all.groupBy { it.category }
            )
        }
    }

    fun updateQuery(q: String) {
        _ui.value = _ui.value.copy(query = q)
    }

    // TAMBAHKAN FUNCTION UNTUK GET RECIPE BY ID
    fun getRecipeById(id: String): Recipe? {
        val allRecipes = dummyRecipes()
        return allRecipes.find { it.id == id }
    }

    // ----- Dummy data -----
    private fun dummyRecipes(): List<Recipe> = listOf(
        // ===== Mie (4) =====
        Recipe("r1","Mie Ayam Indonesia",15,4.6,
            "https://images.unsplash.com/photo-1604908176997-4314a7a0b89b?q=80&w=1200&auto=format&fit=crop","Mie"),
        Recipe("r6","Mie Goreng Jawa Pedas",20,4.7,
            "https://images.unsplash.com/photo-1598866594230-a7c12756260f?q=80&w=1200&auto=format&fit=crop","Mie"),
        Recipe("r9","Mie Kuah Bakso Sederhana",18,4.4,
            "https://images.unsplash.com/photo-1544025162-d76694265947?q=80&w=1200&auto=format&fit=crop","Mie"),
        Recipe("r10","Mie Tek-tek Gerobak Malam",22,4.5,
            "https://images.unsplash.com/photo-1544025161-43f4ce569161?q=80&w=1200&auto=format&fit=crop","Mie"),

        // ===== Nasi (4) =====
        Recipe("r2","Nasi Goreng Rumahan",12,4.5,
            "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=1200&auto=format&fit=crop","Nasi"),
        Recipe("r3","Nasi Uduk Ayam Suwir",40,4.3,
            "https://images.unsplash.com/photo-1550547660-d9450f859349?q=80&w=1200&auto=format&fit=crop","Nasi"),
        Recipe("r7","Nasi Kuning Komplit",60,4.6,
            "https://images.unsplash.com/photo-1589308078059-be1415eab4c3?q=80&w=1200&auto=format&fit=crop","Nasi"),
        Recipe("r8","Nasi Liwet Ikan Teri",45,4.4,
            "https://images.unsplash.com/photo-1604908554023-41a3b3d6c0af?q=80&w=1200&auto=format&fit=crop","Nasi"),

        // ===== Snack (4) =====
        Recipe("r4","Perkedel Kentang Crispy",30,4.4,
            "https://images.unsplash.com/photo-1526318472351-c75fcf070305?q=80&w=1200&auto=format&fit=crop","Snack"),
        Recipe("r5","Putu Ayu Lembut",35,4.2,
            "https://images.unsplash.com/photo-1617471267658-55d5fe8b9e64?q=80&w=1200&auto=format&fit=crop","Snack"),
        Recipe("r11","Pisang Goreng Madu",25,4.5,
            "https://images.unsplash.com/photo-1617093727343-374698d0e5a2?q=80&w=1200&auto=format&fit=crop","Snack"),
        Recipe("r12","Cilok Bumbu Kacang",28,4.3,
            "https://images.unsplash.com/photo-1526318473527-4a2d9f2c8f4e?q=80&w=1200&auto=format&fit=crop","Snack")
    )
}