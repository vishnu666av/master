package com.example.otchallenge.domain

import com.example.otchallenge.data.local.BookEntity

data class BookDomain(
    val title: String,
    val description: String,
    val url: String,
)

fun BookEntity.toDomain() = BookDomain(
    this.title,
    this.description,
    this.image
)