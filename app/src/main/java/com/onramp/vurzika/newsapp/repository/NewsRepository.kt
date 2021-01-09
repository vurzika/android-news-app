package com.onramp.vurzika.newsapp.repository

import com.onramp.vurzika.newsapp.repository.models.NewsArticle
import com.onramp.vurzika.newsapp.repository.services.SpaceFlightNewsApi
import com.onramp.vurzika.newsapp.repository.services.SpaceFlightNewsApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object NewsRepository {

    private val newsService: SpaceFlightNewsApiService by lazy { SpaceFlightNewsApi.service }

    suspend fun getNewsArticles() = withContext(Dispatchers.IO) {
        newsService.getNewsArticles().map {
            NewsArticle(
                    id = it.id,
                    title = it.title,
                    summary = it.summary ?: "Article Summary Not Available",
                    newsSite = it.newsSite ?: "Unknown Source",
                    publicationDate = it.publishedAt,
                    thumbnailUrl = it.imageUrl
            )
        }
    }

    suspend fun getNewsArticle(newsArticleId: String) = withContext(Dispatchers.IO) {
        newsService.getArticle(newsArticleId).let {
            NewsArticle(
                    id = newsArticleId,
                    title = it.title,
                    summary = it.summary ?: "Article Summary Not Available",
                    newsSite = it.newsSite ?: "Unknown Source",
                    publicationDate = it.publishedAt,
                    thumbnailUrl = it.imageUrl
            )
        }
    }
}