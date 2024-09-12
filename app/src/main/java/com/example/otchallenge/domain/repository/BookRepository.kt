package com.example.otchallenge.domain.repository

import com.example.otchallenge.domain.model.Book

interface BookRepository {

    suspend fun getAllBooks(): List<Book>

}