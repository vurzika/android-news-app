package com.onramp.vurzika.newsapp.models

import java.util.Date

data class NewsArticle(
        var id: String,
        var title: String,
        var summary: String,
        var site: String,
        var publicationDate: Date,
        var thumbnailUrl: String?
)
