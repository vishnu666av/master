package com.example.otchallenge.mock

import com.example.otchallenge.domain.BookListContent
import com.example.otchallenge.domain.BookUIModel
import com.example.otchallenge.network.model.BooksItem
import com.example.otchallenge.network.model.BooksResponse
import com.example.otchallenge.network.model.Results

object BookTestFactory {

    fun mockBookUIList(): List<BookListContent> {
        return listOf(
            BookListContent.BookContent(
                bookUIModel = BookUIModel(
                    title = "SWAN SONG",
                    author = "Elin Hilderbrand",
                    contributor = "by Elin Hilderbrand",
                    description = "Nantucket residents are alarmed when a home.",
                    rank = "1",
                    bookImage = "https://storage.googleapis.com/du-prd/books/images/9780316258876.jpg",
                    weeksOnList = 1
                )
            )
        )
    }

    fun mockBooksResponse(): BooksResponse {
        return BooksResponse(
            results = Results(
                books = listOf(
                    BooksItem(
                        title = "SWAN SONG",
                        author = "Elin Hilderbrand",
                        description = "Nantucket residents are alarmed when a home.",
                        rank = 1,
                        bookImage = "https://storage.googleapis.com/du-prd/books/images/9780316258876.jpg"
                    )
                )
            )
        )
    }
}