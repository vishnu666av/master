package com.example.otchallenge.usecase

interface IBooksListUseCase {
    suspend fun getBooks(): BooksListUseCaseResult
}