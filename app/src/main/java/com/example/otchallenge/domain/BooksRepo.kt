package com.example.otchallenge.domain

interface BooksRepo {

    suspend fun getAllBooks(): RepoResponse<List<BookDomain>>
}