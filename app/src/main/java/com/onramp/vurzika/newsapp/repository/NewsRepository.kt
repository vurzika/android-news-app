package com.onramp.vurzika.newsapp.repository

import com.onramp.vurzika.newsapp.repository.database.NewsArticlesDatabase
import com.onramp.vurzika.newsapp.repository.models.NewsArticle
import com.onramp.vurzika.newsapp.repository.services.SpaceFlightNewsApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
        private val database: NewsArticlesDatabase,
        private val newsService: SpaceFlightNewsApiService
) {

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
        }.also {
            it.isStored = database.newsArticlesDao().checkIfExists(newsArticleId)
        }
    }

    suspend fun deleteNewsArticle(newsArticleId: String) = withContext(Dispatchers.IO) {
        database.newsArticlesDao().delete(newsArticleId)
    }

    suspend fun saveNewsArticle(newsArticle: NewsArticle) = withContext(Dispatchers.IO) {
        database.newsArticlesDao().saveNewsArticle(newsArticle)
    }
}