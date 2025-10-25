package com.example.masakin.ui.recipe

data class Recipe(
    val id: String,
    val title: String,
    val minutes: Int,
    val rating: Double,
    val imageUrl: String,
    val category: String
)

data class RecipeUiState(
    val isLoading: Boolean = true,
    val featured: List<Recipe> = emptyList(),
    val categories: Map<String, List<Recipe>> = emptyMap(),
    val query: String = ""
)
