package com.example.newsapp.domain.model

data class Message(
    val id: Long = 0,
    val text: String?,
    val imageUri: String?,
    val timestamp: Long,
    val isSent: Boolean
)
