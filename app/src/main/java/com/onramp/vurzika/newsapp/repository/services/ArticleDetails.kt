package com.onramp.vurzika.newsapp.repository.services

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.util.Date

@JsonIgnoreProperties(ignoreUnknown = true)
data class ArticleDetails(
        val featured: Boolean,
        val title: String,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        val publishedAt: Date,

        val url: String?,
        val imageUrl: String?,
        val newsSite: String?,
        val summary: String?
)