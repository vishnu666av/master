package com.example.otchallenge.domain.models

import com.example.otchallenge.data.network.models.NetBook
import kotlinx.serialization.SerialName

data class Book(
    val rank: Int,
    val title: String,
    val description: String,
    val image: String?
)

fun NetBook.toBook(): Book = Book(
    rank = rank,
    title = title,
    description = description,
    image = image
)
