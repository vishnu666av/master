package com.example.otchallenge.domain.models

import com.example.otchallenge.data.network.models.NetList

data class BookList (
    val name: String,
    val books: List<Book>
)

fun NetList.toBookList():BookList = BookList(
    name = results.listName,
    books =  results.books.map { it.toBook() }
)
