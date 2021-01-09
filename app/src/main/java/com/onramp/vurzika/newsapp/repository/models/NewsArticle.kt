package com.onramp.vurzika.newsapp.repository.models

import java.util.Date

data class NewsArticle(
        val id: String,

        val title: String,
        val summary: String,
        val newsSite: String,
        val publicationDate: Date,
        val thumbnailUrl: String?
)
