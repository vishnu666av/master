package com.example.otchallenge.model

import com.example.otchallenge.data.Book

data class Fiction(
    val rank: Int,
    val title: String,
    val author: String,
    val description: String,
    val imageUrl: String
) {
    companion object {
        fun fromBook(book: Book): Fiction =
            Fiction(
                rank = book.rank,
                title = book.title,
                author = book.author,
                description = book.description,
                imageUrl = book.imageUrl
            )
    }
}