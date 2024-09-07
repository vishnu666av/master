package com.example.otchallenge.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.otchallenge.data.local.BuyLinkListTypeConverter
import com.squareup.moshi.Json

@Entity(tableName = "books")
data class Book(
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
)

data class BuyLink(
    val name: String,
    val url: String
)