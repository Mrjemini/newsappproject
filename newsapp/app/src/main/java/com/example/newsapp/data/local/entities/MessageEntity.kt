package com.example.newsapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val text: String?,
    val imageUri: String?,
    val timestamp: Long,
    val isSent: Boolean
)
