package com.example.otchallenge.data.models

import com.example.otchallenge.data.database.BookEntity
import com.example.otchallenge.data.network.ResponseDataSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Book(
    @SerialName("primary_isbn10")
    val isbn10: String,
    val rank: Int,
    val title: String,
    val description: String,
    val author: String,
    @SerialName("book_image")
    val bookImageUrl: String?,
)

@Serializable
data class NetworkResponse<T>(
    @Serializable(with = ResponseDataSerializer::class)
    val results: ResponseData,
)

fun Book.toBookEntity() = BookEntity(rank, isbn10, title, description, author, bookImageUrl)

@Serializable
sealed class ResponseData

@Serializable
data object EmptyArray : ResponseData()

@Serializable
data class ResultWithData(
    val books: List<Book>,
) : ResponseData()
