package com.example.otchallenge.usecase

import com.example.otchallenge.model.BookDto

sealed class BooksListUseCaseResult {

    data object Empty : BooksListUseCaseResult()

    data class FreshList(val items: List<BookDto>) : BooksListUseCaseResult()

    data class CachedList(val items: List<BookDto>) : BooksListUseCaseResult()

    data object Error : BooksListUseCaseResult()
}