package com.onramp.vurzika.newsapp.repository

import android.content.Context
import android.content.SharedPreferences
import com.onramp.vurzika.newsapp.R
import com.onramp.vurzika.newsapp.repository.database.NewsArticlesDatabase
import com.onramp.vurzika.newsapp.repository.models.NewsArticle
import com.onramp.vurzika.newsapp.repository.services.SpaceFlightNewsApiService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
        private val database: NewsArticlesDatabase,
        private val newsService: SpaceFlightNewsApiService,
        private val userSettings: SharedPreferences,
        @ApplicationContext private val context: Context
) {

    private val lastSeenNewsDateSettingKey by lazy {
        context.getString(R.string.setting_key_last_seen_news_date);
    }

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
        if (database.newsArticlesDao().checkIfExists(newsArticleId)) {
            database.newsArticlesDao().getNewsArticle(newsArticleId).also {
                it.isStored = true
            }
        } else {
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

    suspend fun deleteNewsArticle(newsArticleId: String) = withContext(Dispatchers.IO) {
        database.newsArticlesDao().delete(newsArticleId)
    }

    suspend fun saveNewsArticle(newsArticle: NewsArticle) = withContext(Dispatchers.IO) {
        database.newsArticlesDao().saveNewsArticle(newsArticle)
    }

    suspend fun getSavedNewsArticles(): List<NewsArticle> = withContext(Dispatchers.IO) {
        database.newsArticlesDao().getNewsArticles().onEach {
            it.isStored = true
        }
    }

    suspend fun getLatestAvailableNewsDate(): Date? = withContext(Dispatchers.IO) {
        newsService
                .getNewsArticles(limit = 1)
                .firstOrNull()?.publishedAt
    }

    fun getLastRetrievedNewsDate(): Date? {
        return if (userSettings.contains(lastSeenNewsDateSettingKey)) {
            return Date(userSettings.getLong(lastSeenNewsDateSettingKey, 0))
        } else {
            null
        }
    }

    fun setLastRetrievedNewsDate(date: Date) {
        with(userSettings.edit()) {
            putLong(lastSeenNewsDateSettingKey, date.time)
            apply()
        }
    }
}