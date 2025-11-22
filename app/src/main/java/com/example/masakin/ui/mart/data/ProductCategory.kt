package com.example.masakin.ui.mart.data

import androidx.annotation.DrawableRes
import com.example.masakin.R

enum class ProductCategory(val displayName: String, @DrawableRes val icon: Int) {
    DAGING("Daging", R.drawable.mart_ic_cat_daging),
    BUAH("Buah", R.drawable.mart_ic_cat_buah),
    SAYUR("Sayur", R.drawable.mart_ic_cat_sayur),
    BUMBU("Bumbu", R.drawable.mart_ic_cat_bumbu)
}
