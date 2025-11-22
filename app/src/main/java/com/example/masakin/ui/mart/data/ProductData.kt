package com.example.masakin.ui.mart.data

import androidx.annotation.DrawableRes
import com.example.masakin.R

data class Product(
    val id: String,
    val name: String,
    val price: Int,
    @DrawableRes val image: Int,
    val category: ProductCategory,
    val unit: String = "kg",
    val description: String = ""
)

enum class ProductCategory(val displayName: String, @DrawableRes val icon: Int) {
    DAGING("Daging", R.drawable.mart_ic_cat_daging),
    BUAH("Buah", R.drawable.mart_ic_cat_buah),
    SAYUR("Sayur", R.drawable.mart_ic_cat_sayur),
    BUMBU("Bumbu", R.drawable.mart_ic_cat_bumbu)
}

object ProductRepository {
    private val allProducts = listOf(
        // Daging
        Product("dg1", "Daging Ayam", 25000, R.drawable.mart_ayam, ProductCategory.DAGING),
        Product("dg2", "Daging Sapi", 60000, R.drawable.mart_sapi, ProductCategory.DAGING),
        Product("dg3", "Daging Bebek", 45000, R.drawable.mart_bebek, ProductCategory.DAGING),
        Product("dg4", "Daging Domba", 55000, R.drawable.mart_domba, ProductCategory.DAGING),

        // Buah (tambahkan sesuai kebutuhan)
        Product("bh1", "Pisang", 15000, R.drawable.mart_ayam, ProductCategory.BUAH),
        Product("bh2", "Apel", 30000, R.drawable.mart_ayam, ProductCategory.BUAH),

        // Sayur
        Product("sy1", "Kangkung", 8000, R.drawable.mart_ayam, ProductCategory.SAYUR),
        Product("sy2", "Bayam", 10000, R.drawable.mart_ayam, ProductCategory.SAYUR),

        // Bumbu
        Product("bm1", "Bawang Merah", 35000, R.drawable.mart_ayam, ProductCategory.BUMBU),
        Product("bm2", "Bawang Putih", 40000, R.drawable.mart_ayam, ProductCategory.BUMBU)
    )

    fun getProductsByCategory(category: ProductCategory): List<Product> {
        return allProducts.filter { it.category == category }
    }

    fun searchProducts(query: String): List<Product> {
        return allProducts.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }

    fun getProductById(id: String): Product? {
        return allProducts.find { it.id == id }
    }
}