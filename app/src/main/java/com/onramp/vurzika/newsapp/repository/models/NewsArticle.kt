package com.onramp.vurzika.newsapp.repository.models

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "news_articles")
data class NewsArticle(
        @PrimaryKey
        val id: String,

        val title: String,
        val summary: String,
        val newsSite: String,
        val publicationDate: Date,
        val thumbnailUrl: String?,

        @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
        val thumbnailData: Bitmap? = null
) {
        @Ignore
        var isStored: Boolean = false
}
