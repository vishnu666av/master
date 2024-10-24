package com.example.otchallenge.api.dto

import com.google.gson.annotations.SerializedName

data class BookResultDto(
    @SerializedName("display_name") var listName: String,
    var books: List<BookDto>
) {
    companion object {
        val DEFAULT = BookResultDto("", listOf(BookDto.DEFAULT))
    }
}
