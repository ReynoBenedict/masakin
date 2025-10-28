package com.example.masakin.data.model
data class Post(
    val id: String,
    val userName: String,
    val userHandle: String,
    val userAvatarUrl: String,
    val time: String,
    val content: String,
    val imageUrl: String?,
    val likes: Int,
    val comments: Int,
    val shares: Int
)
