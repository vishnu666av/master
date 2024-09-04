package com.example.otchallenge.presentation.bookList.model

import com.example.otchallenge.common.adapter.ItemModel
import com.example.otchallenge.domain.model.Book

data class BookItemModel(
    val author: String,
    val publisher: String,
    val rank: Int,
    val title: String,
    val description: String,
    val imageUrl: String
) : ItemModel

fun Book.toItemModel() = BookItemModel(
    author = author,
    publisher = publisher,
    rank = rank,
    title = title,
    description = description,
    imageUrl = imageUrl
)