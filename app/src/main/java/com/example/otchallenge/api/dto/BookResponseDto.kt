package com.example.otchallenge.api.dto

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class BookResponseDto(
    @SerializedName("results") val result: BookResultDto,
    val copyright: String,
    @SerializedName("num_results") val totalBooks: Int,
    @SerializedName("last_modified") val lastModified: LocalDateTime
)
