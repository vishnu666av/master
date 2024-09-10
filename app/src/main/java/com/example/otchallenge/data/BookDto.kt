package com.example.otchallenge.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.otchallenge.data.local.BuyLinkListTypeConverter
import com.example.otchallenge.model.BuyLink
import com.squareup.moshi.Json

@Entity(
    tableName = "books",
    indices = [Index(value = ["title", "author"], unique = true)]
)
data class BookDto(
    @Json(ignore = true)
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val rank: Int,
    val title: String,
    val author: String,
    val description: String,

    @Json(name = "book_image")
    val imageUrl: String,

    @Json(name = "buy_links")
    @TypeConverters(BuyLinkListTypeConverter::class)
    val buyLinks: List<BuyLink>
) {
    companion object
}