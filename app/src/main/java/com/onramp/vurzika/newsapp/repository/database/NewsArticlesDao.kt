package com.onramp.vurzika.newsapp.repository.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.onramp.vurzika.newsapp.repository.models.NewsArticle

@Dao
interface NewsArticlesDao {

    @Query("SELECT * FROM news_articles")
    suspend fun getNewsArticles(): List<NewsArticle>

    @Query("SELECT * FROM news_articles WHERE id = :newsArticleId")
    suspend fun getNewsArticle(newsArticleId: String): NewsArticle

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNewsArticle(newsArticle: NewsArticle)

    @Query("DELETE FROM news_articles")
    suspend fun deleteAll();
}