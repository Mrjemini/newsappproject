package com.example.newsapp.data.repository

import com.example.newsapp.data.local.dao.NewsDao
import com.example.newsapp.data.local.entities.NewsEntity
import com.example.newsapp.data.remote.NewsApi
import com.example.newsapp.domain.model.News
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val newsApi: NewsApi,
    private val newsDao: NewsDao
) {
    fun getFeaturedNews(): Flow<List<News>> =
        newsDao.getFeaturedNews().map { entities ->
            entities.map { it.toDomainModel() }
        }

    fun getAllNews(): Flow<List<News>> =
        newsDao.getAllNews().map { entities ->
            entities.map { it.toDomainModel() }
        }

    fun searchNews(query: String): Flow<List<News>> =
        newsDao.searchNews(query).map { entities ->
            entities.map { it.toDomainModel() }
        }

    suspend fun refreshNews(): Result<Unit> {
        return try {
            val response = newsApi.getTopHeadlines(apiKey = NewsApi.API_KEY)
            println("Data ${response.totalResults}")
            val entities = response.articles.mapIndexed { index, dto ->
                NewsEntity(
                    url = dto.url,
                    author = dto.author,
                    title = dto.title,
                    description = dto.description,
                    urlToImage = dto.urlToImage,
                    publishedAt = dto.publishedAt,
                    content = dto.content,
                    isFeatured = index < 5
                )
            }
            newsDao.insertAll(entities)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteExpiredCache() {
        val expiryTime = System.currentTimeMillis() - (24 * 60 * 60 * 1000) // 24 hours
        newsDao.deleteExpiredNews(expiryTime)
    }

    private fun NewsEntity.toDomainModel() = News(
        url = url,
        author = author,
        title = title,
        description = description,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content,
        isFeatured = isFeatured
    )
}