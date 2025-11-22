package com.example.masakin.ui.mart.data

import androidx.annotation.DrawableRes

data class Product(
    val id: Int,
    val name: String,
    val price: Int,
    val unit: String,
    @DrawableRes val image: Int,
    val description: String,
    val nutrition: NutritionInfo,
    val storeName: String,
    val storeLocation: String,
    val relatedRecipes: List<RelatedRecipe>,
    val category: ProductCategory
)
