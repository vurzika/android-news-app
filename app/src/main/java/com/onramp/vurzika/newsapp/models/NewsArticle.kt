package com.onramp.vurzika.newsapp.models

import java.util.*

data class NewsArticle(
        var id: String,
        var title: String,
        var site: String,
        var publicationDate: Date,
        var thumbnailUrl: String?
)
