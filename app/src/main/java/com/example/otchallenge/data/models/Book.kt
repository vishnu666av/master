package com.example.otchallenge.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Book(
    val title: String,
    val description: String,
    val author: String,
    @SerialName("book_image")
    val bookImageUrl: String?,
)

@Serializable
data class NetworkResponse<T>(
    val results: Results<T>,
)

@Serializable
data class Results<T>(
    val books: List<T>,
)
