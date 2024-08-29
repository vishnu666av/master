package com.example.otchallenge.domain.testdata

import com.example.otchallenge.domain.models.Book
import com.example.otchallenge.domain.models.BookList

object PresentationTestData {

    fun buildList(
        name: String = BooksConstants.LIST_NAME,
        books: List<Book> = listOf(buildBook())
    ): BookList = BookList(
        name = name,
        books = books
    )

    fun buildBook(
        primaryIsbn10: String = BooksConstants.BOOK_ISBN,
        rank: Int = BooksConstants.BOOK_RANK,
        title: String = BooksConstants.BOOK_TITLE,
        description: String = BooksConstants.BOOK_DESCRIPTION,
        image: String = BooksConstants.BOOK_IMAGE,
    ): Book = Book(
        primaryIsbn10 = primaryIsbn10,
        rank = rank,
        title = title,
        description = description,
        image = image
    )
}
