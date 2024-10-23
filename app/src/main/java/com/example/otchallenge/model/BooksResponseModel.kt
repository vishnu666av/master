package com.example.otchallenge.model

import com.google.gson.annotations.SerializedName

data class BooksResponseModel(
    @SerializedName("num_results")
    val numResults: Int? = 0,
    val results: BooksResultModel? = null,
)

data class BooksResultModel(val books: List<BookModel>)
