package com.example.otchallenge.domain

/**
 * Represents the content of the book list.
 */
sealed class BookListContent {
    data class BookContent(
        val bookUIModel: BookUIModel
    ) : BookListContent()
}