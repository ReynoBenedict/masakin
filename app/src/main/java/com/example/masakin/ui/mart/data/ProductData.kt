package com.example.masakin.ui.mart.data


import com.example.masakin.R

object ProductRepository {
    private val allProducts = listOf(
        // Daging
        Product(
            id = 1,
            name = "Ayam Potong",
            price = 20000,
            image = R.drawable.mart_ayam,
            category = ProductCategory.DAGING,
            unit = "1kg/pcs",
            description = "Daging ayam yang dijual di pasar biasanya tersedia dalam berbagai bentuk dan potongan, seperti ayam utuh atau dalam potongan-potongan seperti dada, paha atas, paha bawah (drumstick), sayap, dan punggung. Potongan-potongan ini bisa dijual dengan atau tanpa kulit dan tulang, tergantung pada preferensi pembeli. Warna daging ayam mentah biasanya merah muda pucat hingga putih kekuningan, dengan tekstur kenyal dan halus pada bagian dada, sementara bagian paha memiliki tekstur yang sedikit lebih kasar.",
            nutrition = NutritionInfo(165, 74.6, 31, 0, 0, 3.6),
            storeName = "Selvi's Mart",
            storeLocation = "Bali",
            relatedRecipes = listOf(
                RelatedRecipe("r1", "Resep Ayam Goreng! dijamin enak banget!", "William Gozali", R.drawable.mart_ayam),
                RelatedRecipe("r2", "Ayam Rica Rica ala Chef Arnold! Lezatnya...", "Arnold Poernomo", R.drawable.mart_ayam)
            )
        ),
        Product(
            id = 2, 
            name = "Daging Sapi", 
            price = 120000, 
            image = R.drawable.mart_sapi, 
            category = ProductCategory.DAGING,
            unit = "1kg",
            description = "Daging sapi segar pilihan dengan kualitas terbaik. Cocok untuk rendang, soto, atau steak.",
            nutrition = NutritionInfo(250, 60.0, 26, 0, 0, 15.0),
            storeName = "Selvi's Mart",
            storeLocation = "Bali",
            relatedRecipes = emptyList()
        ),
        Product(
            id = 3, 
            name = "Daging Bebek", 
            price = 45000, 
            image = R.drawable.mart_bebek, 
            category = ProductCategory.DAGING,
            unit = "1kg",
            description = "Daging bebek muda yang empuk dan gurih. Sangat pas untuk digoreng atau dibakar.",
            nutrition = NutritionInfo(337, 50.0, 19, 0, 0, 28.0),
            storeName = "Selvi's Mart",
            storeLocation = "Bali",
            relatedRecipes = emptyList()
        ),
        Product(
            id = 4, 
            name = "Daging Domba", 
            price = 55000, 
            image = R.drawable.mart_domba, 
            category = ProductCategory.DAGING,
            unit = "1kg",
            description = "Daging domba segar dengan tekstur lembut. Ideal untuk sate atau gulai.",
            nutrition = NutritionInfo(294, 55.0, 25, 0, 0, 21.0),
            storeName = "Selvi's Mart",
            storeLocation = "Bali",
            relatedRecipes = emptyList()
        ),

        // Buah
        Product(
            id = 5, 
            name = "Buah Pisang", 
            price = 25000, 
            image = R.drawable.mart_ayam, // Placeholder
            category = ProductCategory.BUAH,
            unit = "1 sisir",
            description = "Pisang manis dan legit, kaya akan kalium. Cocok untuk pencuci mulut atau camilan sehat.",
            nutrition = NutritionInfo(89, 75.0, 1, 23, 3, 0.3),
            storeName = "Selvi's Mart",
            storeLocation = "Bali",
            relatedRecipes = emptyList()
        ),
        Product(
            id = 6, 
            name = "Buah Apel", 
            price = 30000, 
            image = R.drawable.mart_ayam, 
            category = ProductCategory.BUAH,
            unit = "1kg",
            description = "Apel merah segar yang renyah dan manis. Sumber serat yang baik.",
            nutrition = NutritionInfo(52, 86.0, 0, 14, 2, 0.2),
            storeName = "Selvi's Mart",
            storeLocation = "Bali",
            relatedRecipes = emptyList()
        ),
        Product(
            id = 7,
            name = "Buah Naga",
            price = 21000,
            image = R.drawable.mart_ayam,
            category = ProductCategory.BUAH,
            unit = "1kg",
            description = "Buah naga merah yang segar dan kaya antioksidan.",
            nutrition = NutritionInfo(60, 87.0, 1, 13, 3, 0.4),
            storeName = "Selvi's Mart",
            storeLocation = "Bali",
            relatedRecipes = emptyList()
        ),

        // Sayur
        Product(
            id = 8, 
            name = "Kangkung", 
            price = 7000, 
            image = R.drawable.mart_ayam, 
            category = ProductCategory.SAYUR,
            unit = "1 ikat",
            description = "Sayur kangkung segar, mudah diolah menjadi tumisan yang lezat.",
            nutrition = NutritionInfo(19, 90.0, 3, 3, 2, 0.2),
            storeName = "Selvi's Mart",
            storeLocation = "Bali",
            relatedRecipes = emptyList()
        ),
        Product(
            id = 9, 
            name = "Bayam", 
            price = 5000, 
            image = R.drawable.mart_ayam, 
            category = ProductCategory.SAYUR,
            unit = "1 ikat",
            description = "Bayam hijau segar, sangat baik untuk sup atau sayur bening.",
            nutrition = NutritionInfo(23, 91.0, 3, 4, 2, 0.4),
            storeName = "Selvi's Mart",
            storeLocation = "Bali",
            relatedRecipes = emptyList()
        ),
        Product(
            id = 10,
            name = "Wortel",
            price = 10000,
            image = R.drawable.mart_ayam,
            category = ProductCategory.SAYUR,
            unit = "1kg",
            description = "Wortel segar kaya vitamin A. Bagus untuk kesehatan mata.",
            nutrition = NutritionInfo(41, 88.0, 1, 10, 3, 0.2),
            storeName = "Selvi's Mart",
            storeLocation = "Bali",
            relatedRecipes = emptyList()
        ),

        // Bumbu
        Product(
            id = 11, 
            name = "Bawang Merah", 
            price = 35000, 
            image = R.drawable.mart_ayam, 
            category = ProductCategory.BUMBU,
            unit = "1kg",
            description = "Bawang merah pilihan, memberikan aroma sedap pada masakan.",
            nutrition = NutritionInfo(72, 80.0, 2, 17, 2, 0.1),
            storeName = "Selvi's Mart",
            storeLocation = "Bali",
            relatedRecipes = emptyList()
        ),
        Product(
            id = 12, 
            name = "Bawang Putih", 
            price = 40000, 
            image = R.drawable.mart_ayam, 
            category = ProductCategory.BUMBU,
            unit = "1kg",
            description = "Bawang putih kating yang wangi dan gurih.",
            nutrition = NutritionInfo(149, 59.0, 6, 33, 2, 0.5),
            storeName = "Selvi's Mart",
            storeLocation = "Bali",
            relatedRecipes = emptyList()
        ),
        Product(
            id = 13,
            name = "Gula",
            price = 12000,
            image = R.drawable.mart_ayam,
            category = ProductCategory.BUMBU,
            unit = "1kg",
            description = "Gula pasir putih berkualitas premium.",
            nutrition = NutritionInfo(387, 0.0, 0, 100, 0, 0.0),
            storeName = "Selvi's Mart",
            storeLocation = "Bali",
            relatedRecipes = emptyList()
        )
    )

    fun getProductsByCategory(category: ProductCategory): List<Product> {
        return allProducts.filter { it.category == category }
    }

    fun searchProducts(query: String): List<Product> {
        return allProducts.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }

    fun getProductById(id: Int): Product? {
        return allProducts.find { it.id == id }
    }
    
    fun getRecentProducts(): List<Product> {
        return allProducts.shuffled().take(6)
    }
}
