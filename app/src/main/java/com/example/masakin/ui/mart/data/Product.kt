package com.example.masakin.ui.mart.data

data class Product(
    val id: Int,
    val name: String,
    val price: Int,
    val unit: String,
    val imageUrl: String,
    val description: String,
    val nutrition: NutritionInfo,
    val storeName: String,
    val storeLocation: String,
    val relatedRecipes: List<RelatedRecipe>,
    val category: ProductCategory
)
