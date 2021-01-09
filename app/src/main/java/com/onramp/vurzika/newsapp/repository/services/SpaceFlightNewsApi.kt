package com.onramp.vurzika.newsapp.repository.services

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface SpaceFlightNewsApiService {
    @GET("/api/v2/articles")
    fun getNewsArticles(): Call<List<ArticleSummary>>

    @GET("/api/v2/articles/{id}")
    fun getArticle(@Path("id") articleId: String): Call<ArticleDetails>
}

object SpaceFlightNewsApi {

    private val retrofit = Retrofit.Builder()
            .addConverterFactory(JacksonConverterFactory.create(jacksonObjectMapper()))
            .baseUrl("https://spaceflightnewsapi.net")
            .build()

    val service: SpaceFlightNewsApiService by lazy { retrofit.create(SpaceFlightNewsApiService::class.java) }
}