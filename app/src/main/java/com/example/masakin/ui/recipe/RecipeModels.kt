package com.example.masakin.ui.recipe

/**
 * Model data untuk resep.
 */
data class Recipe(
    val id: String,
    val name: String,
    val servings: Int,
    val rating: Double,
    val imageRes: Int,
    val category: String
)

data class RecipeUiState(
    val isLoading: Boolean = true,
    val query: String = "",
    val featured: List<Recipe> = emptyList(),
    val categories: Map<String, List<Recipe>> = emptyMap()
)
