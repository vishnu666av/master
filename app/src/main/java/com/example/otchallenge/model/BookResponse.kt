package com.example.otchallenge.model

data class BookResponse(
    val copyright: String,
    val lastModified: String,
    val numResults: Int,
    val results: Results,
    val status: String
)