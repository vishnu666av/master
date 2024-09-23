package com.example.otchallenge.data

/**
 * Represents a book with its details. This object should be used through out the app
 */
data class Book(
    val title: String,
    val author: String,
    val description: String,
    val imageUrl: String?
)
