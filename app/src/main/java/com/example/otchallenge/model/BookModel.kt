package com.example.otchallenge.model

import com.google.gson.annotations.SerializedName

data class BookModel(
    val title: String? = null,
    @SerializedName("book_image")
    val image: String? = null,
    val description: String? = null
)
