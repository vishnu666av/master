package com.example.otchallenge

import com.example.otchallenge.api.BookModel
import com.example.otchallenge.api.BookResponse
import com.example.otchallenge.api.BookResults

internal val bookModel = BookModel("description", "title", "image", "url")
internal val bookResponse = BookResponse(1, BookResults(listOf(bookModel)))
