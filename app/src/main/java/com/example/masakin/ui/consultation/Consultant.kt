package com.example.masakin.ui.consultation

data class Consultant(
    val id: String,
    val name: String,
    val specialization: String,
    val rating: Double,
    val reviews: Int,
    val favorite: Boolean = false
)
