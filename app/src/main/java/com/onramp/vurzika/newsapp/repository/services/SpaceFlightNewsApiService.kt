package com.onramp.vurzika.newsapp.repository.services

import retrofit2.http.GET
import retrofit2.http.Path

interface SpaceFlightNewsApiService {
    @GET("/api/v2/articles")
    suspend fun getNewsArticles(): List<ArticleSummary>

    @GET("/api/v2/articles/{id}")
    suspend fun getArticle(@Path("id") articleId: String): ArticleDetails
}