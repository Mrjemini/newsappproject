package com.example.newsapp.data.remote

import com.example.newsapp.data.remote.dto.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "us",
        @Query("apiKey") apiKey: String,
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 20
    ): NewsResponse

    companion object {
        const val BASE_URL = "https://newsapi.org/"
        const val API_KEY = "a5ffb1d0e4cd4b69ad508aa2dad408c6"
    }
}
