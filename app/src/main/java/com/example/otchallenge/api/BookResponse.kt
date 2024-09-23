package com.example.otchallenge.api

import com.example.otchallenge.data.Book
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookResponse(
    @SerialName("num_results")
    val numResults: Int,
    val results: BookResults,
)

@Serializable
data class BookResults(val books: List<BookModel>)

/**
 * Server model that represents a book object. This should only be used to serialize/deserialize
 * data that deals with server. It should be converted to [Book] to use with anywhere in the app
 */
@Serializable
data class BookModel(
    val description: String,
    val title: String,
    @SerialName("book_image")
    val bookImage: String,
    val author: String
) {
    fun toBook() = Book(title, author, description, bookImage)
}
