package com.example.otchallenge.model.network

import com.example.otchallenge.model.BookDto
import com.squareup.moshi.Json

data class GetBooksResult(
    val copyright: String,

    @Json(name = "last_modified")
    val timestamp: String,

    val results: Results,
)

data class Results(
    val books: List<BookDto>,

    @Json(name = "display_name")
    val displayName: String
)