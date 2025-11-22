package com.example.masakin.ui.consultation

import androidx.annotation.DrawableRes

data class Consultant(
    val id: String,
    val name: String,
    val specialization: String,
    val rating: Double,
    val reviews: Int,
    val favorite: Boolean = false,
    @DrawableRes val imageRes: Int = 0,
    @DrawableRes val favImageRes: Int = 0,

    // Properti tambahan untuk halaman Appoinment
    val patients: String = "0",
    val yearsExperience: String = "0",
    val likes: String = "0",
    val description: String = "",
    val fullName: String = "",
    val workPlace: String = "",
    val availableTime: String = "",
    val price: String = ""
)
