package com.example.otchallenge.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.otchallenge.data.models.Book

@Entity(
    tableName = "books",
)
data class BookEntity(
    @PrimaryKey
    val rank: Int,
    val isbn: String,
    val title: String,
    val description: String,
    val author: String,
    val imageUrl: String?,
)

fun BookEntity.toModel() = Book(isbn, rank, title, description, author, imageUrl)
