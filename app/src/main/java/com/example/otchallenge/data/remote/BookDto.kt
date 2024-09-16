package com.example.otchallenge.data.remote

import com.example.otchallenge.data.local.BookEntity
import com.google.gson.annotations.SerializedName

data class ResponseDto(
    @SerializedName("results")
    val results: ResultsDto
)

data class ResultsDto(
    @SerializedName("books")
    val books: List<BookDto>
)

data class BookDto(
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("book_image")
    val image: String
)

fun BookDto.ToBookEntity() = BookEntity(
    this.title,
    this.description,
    this.image
)