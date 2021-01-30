package com.onramp.vurzika.newsapp.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.room.Room
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.onramp.vurzika.newsapp.repository.database.NewsArticlesDatabase
import com.onramp.vurzika.newsapp.repository.services.SpaceFlightNewsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Singleton
    @Provides
    fun provideNewsArticlesDatabase(@ApplicationContext applicationContext: Context): NewsArticlesDatabase {
        return Room.databaseBuilder(
                applicationContext,
                NewsArticlesDatabase::class.java,
                "news_articles_database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideSpaceFlightNewsApiService(): SpaceFlightNewsApiService {
        val retrofit = Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create(jacksonObjectMapper()))
                .baseUrl("https://spaceflightnewsapi.net")
                .build()

        return retrofit.create(SpaceFlightNewsApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePreferences(@ApplicationContext context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }
}