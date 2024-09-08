package com.example.otchallenge.model

interface BooksListPresenter {

    suspend fun getBooks(): BooksListDataState
}