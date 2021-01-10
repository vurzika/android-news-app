package com.onramp.vurzika.newsapp.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.onramp.vurzika.newsapp.repository.models.NewsArticle

@Database(entities = [NewsArticle::class], version = 1, exportSchema = false)
@TypeConverters(DatabaseTypeConverters::class)
abstract class NewsArticlesDatabase : RoomDatabase() {

    abstract fun newsArticlesDao(): NewsArticlesDao
}