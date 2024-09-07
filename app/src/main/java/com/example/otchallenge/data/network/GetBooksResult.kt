package com.example.otchallenge.data.network

import com.example.otchallenge.data.Book
import com.squareup.moshi.Json

data class GetBooksResult(
    val copyright: String,

    @Json(name = "last_modified")
    val timestamp: String,

    val results: Results,
)

data class Results(
    val books: List<Book>,

    @Json(name = "display_name")
    val displayName: String
)