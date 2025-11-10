package com.example.newsapp.data.local.dao

import androidx.room.*
import com.example.newsapp.data.local.entities.NewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Query("SELECT * FROM news WHERE isFeatured = 1 ORDER BY publishedAt DESC")
    fun getFeaturedNews(): Flow<List<NewsEntity>>

    @Query("SELECT * FROM news WHERE isFeatured = 0 ORDER BY publishedAt DESC")
    fun getAllNews(): Flow<List<NewsEntity>>

    @Query("SELECT * FROM news WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%'")
    fun searchNews(query: String): Flow<List<NewsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(news: List<NewsEntity>)

    @Query("DELETE FROM news")
    suspend fun deleteAll()

    @Query("DELETE FROM news WHERE cachedAt < :expiryTime")
    suspend fun deleteExpiredNews(expiryTime: Long)
}
