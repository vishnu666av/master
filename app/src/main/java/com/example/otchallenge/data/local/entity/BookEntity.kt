package com.example.otchallenge.data.local.entity

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Entity(tableName = "book")
@TypeConverters(UriConverter::class)
data class BookEntity(
    @PrimaryKey
    val bookUri: Uri,
    val author: String,
    val bookImage: String,
    val bookImageHeight: Int,
    val bookImageWidth: Int,
    val contributor: String,
    val contributorNote: String,
    val description: String,
    val price: String,
    val publisher: String,
    val rank: Int,
    val rankLastWeek: Int,
    val title: String,
    val weeksOnList: Int
)

class UriConverter {

    @TypeConverter
    fun fromUri(uri: Uri): String {
        return uri.toString()
    }

    @TypeConverter
    fun toUri(uriString: String): Uri {
        return Uri.parse(uriString)
    }
}
