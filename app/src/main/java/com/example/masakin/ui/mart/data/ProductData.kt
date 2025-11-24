package com.example.masakin.ui.mart.data

object ProductRepository {
    private val allProducts = listOf(
        // Daging
        Product(
            id = 1,
            name = "Ayam Potong",
            price = 20000,
            imageUrl = "https://images.unsplash.com/photo-1604503468506-a8da13d82791?w=400",
            category = ProductCategory.DAGING,
            unit = "1kg/pcs",
            description = "Daging ayam yang dijual di pasar biasanya tersedia dalam berbagai bentuk dan potongan, seperti ayam utuh atau dalam potongan-potongan seperti dada, paha atas, paha bawah (drumstick), sayap, dan punggung. Potongan-potongan ini bisa dijual dengan atau tanpa kulit dan tulang, tergantung pada preferensi pembeli. Warna daging ayam mentah biasanya merah muda pucat hingga putih kekuningan, dengan tekstur kenyal dan halus pada bagian dada, sementara bagian paha memiliki tekstur yang sedikit lebih kasar.",
            nutrition = NutritionInfo(165, 74.6, 31, 0, 0, 3.6),
            storeName = "Selvi's Mart",
            storeLocation = "Bali",
            relatedRecipes = listOf(
                RelatedRecipe("r1", "Resep Ayam Goreng! dijamin enak banget!", "William Gozali", 0),
                RelatedRecipe("r2", "Ayam Rica Rica ala Chef Arnold! Lezatnya...", "Arnold Poernomo", 0)
            )
        ),
        Product(
            id = 2, 
            name = "Daging Sapi", 
            price = 120000, 
            imageUrl = "https://images.unsplash.com/photo-1603048588665-791ca8aea617?w=400",
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
            imageUrl = "https://images.unsplash.com/photo-1583200070600-eb28f4fc7727?w=400",
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
            imageUrl = "https://images.unsplash.com/photo-1588168333986-5078d3ae3976?w=400",
            category = ProductCategory.DAGING,
            unit = "1kg",
            description = "Daging domba segar dengan tekstur lembut. Ideal untuk sate atau gulai.",
            nutrition = NutritionInfo(294, 55.0, 25, 0, 0, 21.0),
            storeName = "Selvi's Mart",
            storeLocation = "Bali",
            relatedRecipes = emptyList()
        ),
        Product(
            id = 14,
            name = "Daging Kambing",
            price = 85000,
            imageUrl = "https://images.unsplash.com/photo-1529692236671-f1f6cf9683ba?w=400",
            category = ProductCategory.DAGING,
            unit = "1kg",
            description = "Daging kambing premium tanpa lemak berlebih, sangat cocok untuk gulai kambing atau sate kambing yang lezat.",
            nutrition = NutritionInfo(143, 64.0, 27, 0, 0, 3.0),
            storeName = "Selvi's Mart",
            storeLocation = "Bali",
            relatedRecipes = emptyList()
        ),
        Product(
            id = 15,
            name = "Paha Ayam Fillet",
            price = 35000,
            imageUrl = "https://images.unsplash.com/photo-1587593810167-a84920ea0781?w=400",
            category = ProductCategory.DAGING,
            unit = "500g",
            description = "Fillet paha ayam tanpa tulang, perfect untuk dimasak sebagai ayam teriyaki atau ayam panggang.",
            nutrition = NutritionInfo(209, 67.0, 26, 0, 0, 11.0),
            storeName = "Selvi's Mart",
            storeLocation = "Bali",
            relatedRecipes = emptyList()
        ),
        Product(
            id = 16,
            name = "Daging Sapi Wagyu",
            price = 250000,
            imageUrl = "https://images.unsplash.com/photo-1558030006-450675393462?w=400",
            category = ProductCategory.DAGING,
            unit = "500g",
            description = "Daging sapi wagyu premium marbling sempurna, sangat lembut dan cocok untuk steak atau yakiniku.",
            nutrition = NutritionInfo(317, 50.0, 21, 0, 0, 26.0),
            storeName = "Selvi's Mart",
            storeLocation = "Bali",
            relatedRecipes = emptyList()
        ),
        Product(
            id = 17,
            name = "Dada Ayam Fillet",
            price = 38000,
            imageUrl = "https://images.unsplash.com/photo-1604503468506-a8da13d82791?w=400",
            category = ProductCategory.DAGING,
            unit = "500g",
            description = "Fillet dada ayam tanpa kulit dan tulang, rendah lemak tinggi protein untuk diet sehat dan chicken katsu.",
            nutrition = NutritionInfo(165, 74.0, 31, 0, 0, 3.6),
            storeName = "Selvi's Mart",
            storeLocation = "Bali",
            relatedRecipes = emptyList()
        ),
        Product(
            id = 18,
            name = "Iga Sapi Premium",
            price = 140000,
            imageUrl = "https://images.unsplash.com/photo-1544025162-d76694265947?w=400",
            category = ProductCategory.DAGING,
            unit = "1kg",
            description = "Iga sapi premium dengan daging tebal menempel pada tulang, ideal untuk sup iga atau sop konro.",
            nutrition = NutritionInfo(291, 57.0, 25, 0, 0, 20.0),
            storeName = "Selvi's Mart",
            storeLocation = "Bali",
            relatedRecipes = emptyList()
        ),
        Product(
            id = 19,
            name = "Daging Bebek Peking",
            price = 55000,
            imageUrl = "https://images.unsplash.com/photo-1598103442097-8b74394b95c6?w=400",
            category = ProductCategory.DAGING,
            unit = "800g",
            description = "Bebek Peking premium dengan kulit renyah ketika dipanggang, sangat cocok untuk bebek panggang ala Tionghoa.",
            nutrition = NutritionInfo(404, 44.0, 23, 0, 0, 35.0),
            storeName = "Selvi's Mart",
            storeLocation = "Bali",
            relatedRecipes = emptyList()
        ),
        Product(
            id = 20,
            name = "Tenderloin Sapi",
            price = 180000,
            imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400",
            category = ProductCategory.DAGING,
            unit = "500g",
            description = "Tenderloin sapi Australia import, bagian paling empuk dari sapi, sempurna untuk steak medium rare.",
            nutrition = NutritionInfo(227, 59.0, 26, 0, 0, 13.0),
            storeName = "Selvi's Mart",
            storeLocation = "Bali",
            relatedRecipes = emptyList()
        ),
        Product(
            id = 21,
            name = "Sayap Ayam",
            price = 28000,
            imageUrl = "https://images.unsplash.com/photo-1562158147-f07f83d0b9f3?w=400",
            category = ProductCategory.DAGING,
            unit = "500g",
            description = "Sayap ayam segar untuk buffalo wings, chicken wings BBQ atau sayap goreng tepung yang renyah.",
            nutrition = NutritionInfo(203, 68.0, 30, 0, 0, 8.0),
            storeName = "Selvi's Mart",
            storeLocation = "Bali",
            relatedRecipes = emptyList()
        ),

        // Buah
        Product(
            id = 5, 
            name = "Buah Pisang", 
            price = 25000, 
            imageUrl = "https://images.unsplash.com/photo-1571771894821-ce9b6c11b08e?w=400",
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
            imageUrl = "https://images.unsplash.com/photo-1560806887-1e4cd0b6cbd6?w=400",
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
            imageUrl = "https://images.unsplash.com/photo-1603055381112-31b780755dea?w=400",
            category = ProductCategory.BUAH,
            unit = "1kg",
            description = "Buah naga merah yang segar dan kaya antioksidan.",
            nutrition = NutritionInfo(60, 87.0, 1, 13, 3, 0.4),
            storeName = "Selvi's Mart",
            storeLocation = "Bali",
            relatedRecipes = emptyList()
        ),
        Product(
            id = 22,
            name = "Jeruk Sunkist",
            price = 35000,
            imageUrl = "https://images.unsplash.com/photo-1547514701-42782101795e?w=400",
            category = ProductCategory.BUAH,
            unit = "1kg",
            description = "Jeruk sunkist manis segar dari California, kaya vitamin C untuk tingkatkan daya tahan tubuh.",
            nutrition = NutritionInfo(47, 87.0, 1, 12, 2, 0.1),
            storeName = "Selvi's Mart",
            storeLocation = "Bali",
            relatedRecipes = emptyList()
        ),
        Product(
            id = 23,
            name = "Mangga Harum Manis",
            price = 28000,
            imageUrl = "https://images.unsplash.com/photo-1553279768-865429fa0078?w=400",
            category = ProductCategory.BUAH,
            unit = "1kg",
            description = "Mangga harum manis matang pohon, sangat manis dan harum sempurna untuk jus mangga atau dimakan langsung.",
            nutrition = NutritionInfo(60, 84.0, 1, 15, 2, 0.4),
            storeName = "Selvi's Mart",
            storeLocation = "Bali",
            relatedRecipes = emptyList()
        ),
        Product(
            id = 24,
            name = "Semangka Merah",
            price = 15000,
            imageUrl = "https://images.unsplash.com/photo-1587049352846-4a222e784acc?w=400",
            category = ProductCategory.BUAH,
            unit = "1kg",
            description = "Semangka merah manis tanpa biji, sangat segar dan cocok untuk dimakan saat cuaca panas.",
            nutrition = NutritionInfo(30, 92.0, 1, 8, 0, 0.2),
            storeName = "Selvi's Mart",
            storeLocation = "Bali",
            relatedRecipes = emptyList()
        ),
        Product(
            id = 25,
            name = "Anggur Hijau",
            price = 45000,
            imageUrl = "https://images.unsplash.com/photo-1599819177626-c2f85751c441?w=400",
            category = ProductCategory.BUAH,
            unit = "500g",
            description = "Anggur hijau seedless tanpa biji dari Australia, manis segar untuk camilan atau fruit salad.",
            nutrition = NutritionInfo(69, 81.0, 1, 18, 1, 0.2),
            storeName = "Selvi's Mart",
            storeLocation = "Bali",
            relatedRecipes = emptyList()
        ),
        Product(
            id = 26,
            name = "Strawberry Segar",
            price = 50000,
            imageUrl = "https://images.unsplash.com/photo-1464965911861-746a04b4bca6?w=400",
            category = ProductCategory.BUAH,
            unit = "250g",
            description = "Strawberry segar merah merona dari Bandung, manis asam segar untuk topping cake atau smoothie.",
            nutrition = NutritionInfo(32, 91.0, 1, 8, 2, 0.3),
            storeName = "Selvi's Mart",
            storeLocation = "Bali",
            relatedRecipes = emptyList()
        ),
        Product(
            id = 27,
            name = "Pepaya California",
            price = 12000,
            imageUrl = "https://images.unsplash.com/photo-1526318472351-c75fcf070305?w=400",
            category = ProductCategory.BUAH,
            unit = "1kg",
            description = "Pepaya California manis merah oranye, kaya enzim papain baik untuk pencernaan.",
            nutrition = NutritionInfo(43, 88.0, 0, 11, 2, 0.3),
            storeName = "Selvi's Mart",
            storeLocation = "Bali",
            relatedRecipes = emptyList()
        ),
        Product(
            id = 28,
            name = "Pir Hijau Premium",
            price = 40000,
            imageUrl = "https://images.unsplash.com/photo-1568477620466-4730f0c1f5e9?w=400",
            category = ProductCategory.BUAH,
            unit = "1kg",
            description = "Pir hijau Packham import, renyah manis segar untuk dimakan langsung atau fruit platter.",
            nutrition = NutritionInfo(57, 84.0, 0, 15, 3, 0.1),
            storeName = "Selvi's Mart",
            storeLocation = "Bali",
            relatedRecipes = emptyList()
        ),
        Product(
            id = 29,
            name = "Kiwi Hijau",
            price = 48000,
            imageUrl = "https://images.unsplash.com/photo-1585059895524-72359e06133a?w=400",
            category = ProductCategory.BUAH,
            unit = "500g",
            description = "Kiwi hijau segar dari Selandia Baru, kaya vitamin C dan serat untuk kesehatan optimal.",
            nutrition = NutritionInfo(61, 83.0, 1, 15, 3, 0.5),
            storeName = "Selvi's Mart",
            storeLocation = "Bali",
            relatedRecipes = emptyList()
        ),

        // Sayur
        Product(
            id = 8, 
            name = "Kangkung", 
            price = 7000, 
            imageUrl = "https://images.unsplash.com/photo-1576045057995-568f588f82fb?w=400",
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
            imageUrl = "https://images.unsplash.com/photo-1576045057995-568f588f82fb?w=400",
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
            imageUrl = "https://images.unsplash.com/photo-1598170845058-32b9d6a5da37?w=400",
            category = ProductCategory.SAYUR,
            unit = "1kg",
            description = "Wortel segar kaya vitamin A. Bagus untuk kesehatan mata.",
            nutrition = NutritionInfo(41, 88.0, 1, 10, 3, 0.2),
            storeName = "Selvi's Mart",
            storeLocation = "Bali",
            relatedRecipes = emptyList()
        ),
        Product(
            id = 30,
            name = "Brokoli Hijau",
            price = 18000,
            imageUrl = "https://images.unsplash.com/photo-1459411621453-7b03977f4bfc?w=400",
            category = ProductCategory.SAYUR,
            unit = "500g",
            description = "Brokoli hijau segar organik, superfood kaya vitamin K dan C untuk kesehatan tulang dan imunitas.",
            nutrition = NutritionInfo(34, 89.0, 3, 7, 3, 0.4),
            storeName = "Selvi's Mart",
            storeLocation = "Bali",
            relatedRecipes = emptyList()
        ),
        Product(
            id = 31,
            name = "Tomat Merah",
            price = 12000,
            imageUrl = "https://images.unsplash.com/photo-1546094096-0df4bcaaa337?w=400",
            category = ProductCategory.SAYUR,
            unit = "1kg",
            description = "Tomat merah segar dan manis, kaya likopen antioksidan untuk tumisan, sambal atau salad.",
            nutrition = NutritionInfo(18, 95.0, 1, 4, 1, 0.2),
            storeName = "Selvi's Mart",
            storeLocation = "Bali",
            relatedRecipes = emptyList()
        ),
        Product(
            id = 32,
            name = "Jagung Manis",
            price = 8000,
            imageUrl = "https://images.unsplash.com/photo-1551754655-cd27e38d2076?w=400",
            category = ProductCategory.SAYUR,
            unit = "3 buah",
            description = "Jagung manis segar dari ladang, sangat manis dan lezat direbus atau dibakar untuk jagung bakar.",
            nutrition = NutritionInfo(86, 76.0, 3, 19, 2, 1.4),
            storeName = "Selvi's Mart",
            storeLocation = "Bali",
            relatedRecipes = emptyList()
        ),
        Product(
            id = 33,
            name = "Kentang Granola",
            price = 15000,
            imageUrl = "https://images.unsplash.com/photo-1518977676601-b53f82aba655?w=400",
            category = ProductCategory.SAYUR,
            unit = "1kg",
            description = "Kentang granola kualitas premium dari Dieng, tekstur pulen cocok untuk kentang goreng atau mashed potato.",
            nutrition = NutritionInfo(77, 79.0, 2, 17, 2, 0.1),
            storeName = "Selvi's Mart",
            storeLocation = "Bali",
            relatedRecipes = emptyList()
        ),
        Product(
            id = 34,
            name = "Sawi Hijau",
            price = 6000,
            imageUrl = "https://images.unsplash.com/photo-1590165482129-1b8b27698780?w=400",
            category = ProductCategory.SAYUR,
            unit = "1 ikat",
            description = "Sawi hijau segar organik tanpa pestisida, sempurna untuk cah sawi atau sop sayuran.",
            nutrition = NutritionInfo(13, 95.0, 2, 2, 2, 0.2),
            storeName = "Selvi's Mart",
            storeLocation = "Bali",
            relatedRecipes = emptyList()
        ),
        Product(
            id = 35,
            name = "Paprika Merah",
            price = 22000,
            imageUrl = "https://images.unsplash.com/photo-1563565375-f3fdfdbefa83?w=400",
            category = ProductCategory.SAYUR,
            unit = "250g",
            description = "Paprika merah manis premium import, kaya vitamin C untuk tumis atau salad segar.",
            nutrition = NutritionInfo(31, 92.0, 1, 6, 2, 0.3),
            storeName = "Selvi's Mart",
            storeLocation = "Bali",
            relatedRecipes = emptyList()
        ),
        Product(
            id = 36,
            name = "Timun Jepang",
            price = 9000,
            imageUrl = "https://images.unsplash.com/photo-1589927986089-35812388d1f1?w=400",
            category = ProductCategory.SAYUR,
            unit = "500g",
            description = "Timun Jepang renyah segar tanpa biji untuk lalapan, acar atau salad mentimun yang menyegarkan.",
            nutrition = NutritionInfo(16, 95.0, 1, 4, 1, 0.1),
            storeName = "Selvi's Mart",
            storeLocation = "Bali",
            relatedRecipes = emptyList()
        ),
        Product(
            id = 37,
            name = "Cabe Merah Keriting",
            price = 50000,
            imageUrl = "https://images.unsplash.com/photo-1583454155184-870a1f63eebc?w=400",
            category = ProductCategory.SAYUR,
            unit = "250g",
            description = "Cabe merah keriting segar pedas mantap untuk sambal, tumisan atau masakan pedas favorit Anda.",
            nutrition = NutritionInfo(40, 88.0, 2, 9, 2, 0.4),
            storeName = "Selvi's Mart",
            storeLocation = "Bali",
            relatedRecipes = emptyList()
        ),

        // Bumbu
        Product(
            id = 11, 
            name = "Bawang Merah", 
            price = 35000, 
            imageUrl = "https://images.unsplash.com/photo-1618512496248-a07fe83aa8cb?w=400",
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
            imageUrl = "https://images.unsplash.com/photo-1588184344047-2f0b9b55b0b5?w=400",
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
            imageUrl = "https://images.unsplash.com/photo-1587735243615-c03f25aaff15?w=400",
            category = ProductCategory.BUMBU,
            unit = "1kg",
            description = "Gula pasir putih berkualitas premium.",
            nutrition = NutritionInfo(387, 0.0, 0, 100, 0, 0.0),
            storeName = "Selvi's Mart",
            storeLocation = "Bali",
            relatedRecipes = emptyList()
        ),
        Product(
            id = 38,
            name = "Merica Hitam Bubuk",
            price = 25000,
            imageUrl = "https://images.unsplash.com/photo-1599909533730-f3908a98c9c6?w=400",
            category = ProductCategory.BUMBU,
            unit = "100g",
            description = "Merica hitam bubuk asli kualitas premium, aroma kuat untuk seasoning steak atau masakan Western.",
            nutrition = NutritionInfo(251, 10.0, 10, 64, 25, 3.3),
            storeName = "Selvi's Mart",
            storeLocation = "Bali",
            relatedRecipes = emptyList()
        ),
        Product(
            id = 39,
            name = "Garam Himalaya",
            price = 30000,
            imageUrl = "https://images.unsplash.com/photo-1605061829796-ac2cdeb1ad40?w=400",
            category = ProductCategory.BUMBU,
            unit = "250g",
            description = "Garam Himalaya pink salt alami kaya mineral untuk masakan sehat dan lebih gurih.",
            nutrition = NutritionInfo(0, 0.0, 0, 0, 0, 0.0),
            storeName = "Selvi's Mart",
            storeLocation = "Bali",
            relatedRecipes = emptyList()
        ),
        Product(
            id = 40,
            name = "Kunyit Bubuk",
            price = 18000,
            imageUrl = "https://images.unsplash.com/photo-1615485500834-bc10199bc768?w=400",
            category = ProductCategory.BUMBU,
            unit = "50g",
            description = "Kunyit bubuk asli tanpa campuran untuk bumbu kuning, kari atau jamu tradisional Indonesia.",
            nutrition = NutritionInfo(312, 13.0, 10, 67, 21, 3.3),
            storeName = "Selvi's Mart",
            storeLocation = "Bali",
            relatedRecipes = emptyList()
        ),
        Product(
            id = 41,
            name = "Ketumbar Bubuk",
            price = 15000,
            imageUrl = "https://images.unsplash.com/photo-1596040033229-a0b7e2a97348?w=400",
            category = ProductCategory.BUMBU,
            unit = "100g",
            description = "Ketumbar bubuk murni aroma harum untuk bumbu rendang, opor ayam atau masakan tradisional.",
            nutrition = NutritionInfo(298, 9.0, 12, 55, 42, 16.0),
            storeName = "Selvi's Mart",
            storeLocation = "Bali",
            relatedRecipes = emptyList()
        ),
        Product(
            id = 42,
            name = "Kecap Manis Premium",
            price = 20000,
            imageUrl = "https://images.unsplash.com/photo-1626200419199-391ae4be7a41?w=400",
            category = ProductCategory.BUMBU,
            unit = "600ml",
            description = "Kecap manis premium kental legit untuk marinasi ayam bakar, nasi goreng atau semur daging.",
            nutrition = NutritionInfo(140, 60.0, 2, 33, 0, 0.0),
            storeName = "Selvi's Mart",
            storeLocation = "Bali",
            relatedRecipes = emptyList()
        ),
        Product(
            id = 43,
            name = "Saus Tiram",
            price = 22000,
            imageUrl = "https://images.unsplash.com/photo-1617696990807-1f0c1a6e6122?w=400",
            category = ProductCategory.BUMBU,
            unit = "510g",
            description = "Saus tiram original dari tiram asli untuk cah kangkung, cap cay atau mie goreng yang gurih.",
            nutrition = NutritionInfo(51, 67.0, 1, 11, 0, 0.3),
            storeName = "Selvi's Mart",
            storeLocation = "Bali",
            relatedRecipes = emptyList()
        ),
        Product(
            id = 44,
            name = "Minyak Goreng Premium",
            price = 28000,
            imageUrl = "https://images.unsplash.com/photo-1474979266404-7eaacbcd87c5?w=400",
            category = ProductCategory.BUMBU,
            unit = "2 liter",
            description = "Minyak goreng premium jernih tidak mudah berbusa untuk menggoreng lebih sehat dan renyah.",
            nutrition = NutritionInfo(884, 0.0, 0, 0, 0, 100.0),
            storeName = "Selvi's Mart",
            storeLocation = "Bali",
            relatedRecipes = emptyList()
        ),
        Product(
            id = 45,
            name = "Lengkuas Segar",
            price = 10000,
            imageUrl = "https://images.unsplash.com/photo-1577234286642-fc512a5f8f11?w=400",
            category = ProductCategory.BUMBU,
            unit = "250g",
            description = "Lengkuas segar harum kuat untuk bumbu soto ayam, rendang atau tom yam yang autentik.",
            nutrition = NutritionInfo(71, 82.0, 1, 15, 2, 1.0),
            storeName = "Selvi's Mart",
            storeLocation = "Bali",
            relatedRecipes = emptyList()
        )
    )

    fun getProductsByCategory(category: ProductCategory?): List<Product> {
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
