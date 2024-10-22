package com.example.otchallenge.dto

import com.google.gson.annotations.SerializedName

data class BookResultDto(
    @SerializedName("display_name") var listName: String,
    var books: List<BookDto>
)
