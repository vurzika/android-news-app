package com.onramp.vurzika.newsapp.repository.database

import android.graphics.Bitmap

import android.graphics.BitmapFactory

import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream
import java.util.Date


class DatabaseTypeConverters {

    // Storing Dates as timestamps
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    // Storing Bitmap as ByteArray as
    @TypeConverter
    fun fromByteArrayToBitmap(data: ByteArray?): Bitmap? {
        return if (data != null) {
            BitmapFactory.decodeByteArray(data, 0, data.size)
        } else {
            null
        }
    }

    @TypeConverter
    fun fromBitmapToByteArray(bitmap: Bitmap?): ByteArray? {
        return if (bitmap != null) {
            val byteArrayStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayStream)
            byteArrayStream.toByteArray()
        } else {
            null
        }
    }
}