package com.example.masakin.data

import com.example.masakin.R
import com.example.masakin.ui.consultation.Consultant

object DummyData {
    val consultants = listOf(
        Consultant(
            id = "1",
            name = "Dr. Trini",
            specialization = "Ahli Gizi Klinis",
            rating = 4.6,
            reviews = 1800,
            favorite = true,
            imageRes = R.drawable.topconsult1,
            favImageRes = R.drawable.consult1,
            patients = "796 +",
            yearsExperience = "6 +",
            likes = "11.3K",
            description = "Trini adalah seorang ahli gizi profesional yang berpengalaman dalam memberikan konsultasi gizi dan merancang program nutrisi yang disesuaikan untuk berbagai kebutuhan individu. Dengan latar belakang pendidikan yang kuat dalam ilmu gizi dan dietetik, Trini memiliki pemahaman mendalam tentang kebutuhan nutrisi pada setiap tahap kehidupan, mulai dari anak-anak hingga lansia.\n\nDengan keahlian dan komitmennya, Trini berperan sebagai konsultan yang dapat diandalkan dalam mendukung setiap individu mencapai kesehatan optimal dan meningkatkan kualitas hidup melalui pola makan yang sehat dan gaya hidup seimbang.",
            fullName = "Trini Wahyuni S.doc",
            workPlace = "Panti Waluya",
            availableTime = "12:00-17:00",
            price = "Rp 60.000"
        ),
        Consultant(
            id = "2",
            name = "Dr. Sally",
            specialization = "Pengawasan Makanan",
            rating = 4.3,
            reviews = 210,
            favorite = true,
            imageRes = R.drawable.topconsult2,
            favImageRes = R.drawable.consult2,
            patients = "500 +",
            yearsExperience = "4 +",
            likes = "8.1K",
            description = "Dr. Sally adalah seorang ahli gizi yang berfokus pada pengawasan makanan dan keamanan pangan. Dia memiliki keahlian dalam memastikan bahwa makanan yang dikonsumsi aman dan memenuhi standar kualitas.",
            fullName = "Dr. Sally L, M.Gizi",
            workPlace = "RS. Sehat Selalu",
            availableTime = "09:00-15:00",
            price = "Rp 55.000"
        ),
        Consultant(
            id = "3",
            name = "Dr. Lisa",
            specialization = "Penilaian Gizi",
            rating = 4.7,
            reviews = 567,
            imageRes = R.drawable.topconsult3,
            patients = "650 +",
            yearsExperience = "5 +",
            likes = "9.8K",
            description = "Dr. Lisa adalah spesialis dalam penilaian gizi. Dia membantu klien memahami status gizi mereka dan memberikan rekomendasi untuk perbaikan.",
            fullName = "Dr. Lisa M, S.Gz",
            workPlace = "Klinik Gizi Prima",
            availableTime = "10:00-16:00",
            price = "Rp 62.000"
        ),
        Consultant(
            id = "4",
            name = "Dr. Richard Lee",
            specialization = "Perencanaan Diet",
            rating = 4.7,
            reviews = 647,
            imageRes = R.drawable.topconsult4,
            patients = "700 +",
            yearsExperience = "7 +",
            likes = "12.5K",
            description = "Dr. Richard Lee ahli dalam merancang program diet yang efektif untuk berbagai tujuan, termasuk penurunan berat badan, manajemen penyakit, dan peningkatan performa atletik.",
            fullName = "Dr. Richard Lee, Sp.GK",
            workPlace = "Pusat Kebugaran FitLife",
            availableTime = "11:00-18:00",
            price = "Rp 70.000"
        ),
        Consultant(
            id = "5",
            name = "Dr. Sinta",
            specialization = "Pengawasan Makanan",
            rating = 4.8,
            reviews = 765,
            imageRes = R.drawable.topconsult5,
            patients = "800 +",
            yearsExperience = "8 +",
            likes = "15.2K",
            description = "Dr. Sinta memiliki pengalaman luas dalam pengawasan makanan dan gizi masyarakat. Dia aktif dalam kampanye makanan sehat dan bergizi untuk masyarakat luas.",
            fullName = "Dr. Sinta B, M.Sc",
            workPlace = "Dinas Kesehatan Kota",
            availableTime = "08:00-14:00",
            price = "Rp 50.000"
        ),
    )
}