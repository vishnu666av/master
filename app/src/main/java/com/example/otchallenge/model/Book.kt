package com.example.otchallenge.model

import androidx.room.Entity
import androidx.room.PrimaryKey
//import androidx.room.ColumnInfo

@Entity(tableName = "books")
data class Book(
    @PrimaryKey
    val primary_isbn13: String,
    val rank: Int,
    val title: String,
    val author: String,
    val description: String,
    val book_image: String
)
