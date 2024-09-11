package com.example.otchallenge.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
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
) {
    companion object
}