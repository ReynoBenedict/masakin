package com.example.masakin.ui.screens.recipe

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.masakin.ui.recipe.RecipeViewModel
import com.example.masakin.ui.recipe.Recipe

@Composable
fun RecipeDetailRoute(
    recipeId: String,
    viewModel: RecipeViewModel,
    onBack: () -> Unit,

) {
    // ambil data resep dari ViewModel (kamu menambahkan fungsi getRecipeById di vm sebelumnya)
    val recipe: Recipe? = remember { viewModel.getRecipeById(recipeId) }

    if (recipe == null) {
        // fallback sederhana
        androidx.compose.material3.Text("Resep tidak ditemukan")
        return
    }

    RecipeDetailScreen(
        recipe = recipe,
        onBack = onBack
    )
}
