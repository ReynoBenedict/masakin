package com.example.masakin.ui.mart.data

import androidx.annotation.DrawableRes

data class RelatedRecipe(
    val id: String,
    val title: String,
    val chef: String,
    @DrawableRes val image: Int
)
