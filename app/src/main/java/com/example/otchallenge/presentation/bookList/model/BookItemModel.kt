package com.example.otchallenge.presentation.bookList.model

import com.example.otchallenge.common.adapter.ItemModel
import com.example.otchallenge.domain.model.Book

data class BookItemModel(
    val title: String,
    val description: String,
    val imageUrl: String
) : ItemModel

fun Book.toItemModel() = BookItemModel(
    title = title,
    description = description,
    imageUrl = imageUrl
)