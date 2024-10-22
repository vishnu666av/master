package com.example.otchallenge.api.dto

import com.google.gson.annotations.SerializedName

data class BookDto(
    val rank: Int,
    val publisher: String,
    val title: String,
    val author: String,
    val contributor: String,
    @SerializedName("book_image") val bookImage: String,
    @SerializedName("primary_isbn13") val isbn: String,
    val description: String
)