package com.example.newsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsapp.data.local.dao.MessageDao
import com.example.newsapp.data.local.dao.NewsDao
import com.example.newsapp.data.local.entities.MessageEntity
import com.example.newsapp.data.local.entities.NewsEntity

@Database(
    entities = [NewsEntity::class, MessageEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
    abstract fun messageDao(): MessageDao
}