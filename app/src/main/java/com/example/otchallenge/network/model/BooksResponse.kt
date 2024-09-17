package com.example.otchallenge.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BooksResponse(
    @Json(name = "results")
    val results: Results? = null,
)

@JsonClass(generateAdapter = true)
data class Results(
    @Json(name = "display_name")
    val displayName: String? = null,

    @Json(name = "books")
    val books: List<BooksItem?>? = null,
)

@JsonClass(generateAdapter = true)
data class BooksItem(
    @Json(name = "title")
    val title: String? = null,

    @Json(name = "description")
    val description: String? = null,

    @Json(name = "author")
    val author: String? = null,

    @Json(name = "book_image")
    val bookImage: String? = null,

    @Json(name = "rank")
    val rank: Int? = null,

    @Json(name = "contributor")
    val contributor: String? = null,

    @Json(name = "publisher")
    val publisher: String? = null,

    @Json(name = "weeks_on_list")
    val weeksOnList: Int? = null,
)
