package com.example.otchallenge.domain.models

import com.example.otchallenge.data.network.models.NetBook

data class Book(
    val primaryIsbn10: String,
    val rank: Int,
    val title: String,
    val description: String,
    val image: String?
)

fun NetBook.toBook(): Book = Book(
    primaryIsbn10 = primaryIsbn10,
    rank = rank,
    title = title,
    description = description,
    image = image
)
