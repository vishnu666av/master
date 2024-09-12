package com.example.otchallenge.data.remote.dto

import com.google.gson.annotations.SerializedName

data class BookDto(
    val author: String,
    @SerializedName("book_image")
    val bookImage: String,
    @SerializedName("book_image_height")
    val bookImageHeight: Int,
    @SerializedName("book_image_width")
    val bookImageWidth: Int,
    @SerializedName("book_uri")
    val bookUri: String,
    val contributor: String,
    @SerializedName("contributor_note")
    val contributorNote: String,
    val description: String,
    val price: String,
    val publisher: String,
    val rank: Int,
    @SerializedName("rank_last_week")
    val rankLastWeek: Int,
    val title: String,
    @SerializedName("weeks_on_list")
    val weeksOnList: Int
)