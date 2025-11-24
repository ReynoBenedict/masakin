package com.example.masakin.data.model

data class Post(
    val id: String,
    val userName: String,
    val userHandle: String,
    val userAvatarUrl: String,
    val time: String,
    val content: String,
    val imageUrl: String? = null,
    val likes: Int = 0,
    val comments: Int = 0,
    val shares: Int = 0
)
