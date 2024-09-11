package com.example.otchallenge.fixtures

import com.example.otchallenge.model.BookDto

fun BookDto.Companion.testPrototype() = BookDto(
    rank = 1,
    title = "book title",
    author = "book author",
    description = "book description",
    imageUrl = "book imageUrk",
    buyLinks = listOf()
)

fun BookDto.Companion.testPrototypeList() = listOf(testPrototype())