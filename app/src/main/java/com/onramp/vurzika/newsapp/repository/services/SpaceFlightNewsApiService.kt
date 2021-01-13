package com.onramp.vurzika.newsapp.repository.services

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SpaceFlightNewsApiService {

    @GET("/api/v2/articles")
    suspend fun getNewsArticles(
            @Query("_contains") contains: String? = null,
            @Query("_limit") limit: Int? = null): List<ArticleSummary>

    @GET("/api/v2/articles/{id}")
    suspend fun getArticle(@Path("id") articleId: String): ArticleDetails
}