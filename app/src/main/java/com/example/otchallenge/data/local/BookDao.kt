package com.example.otchallenge.data.local

interface BookDao {

    suspend fun saveAll(books: List<BookEntity>)
    suspend fun getAll() : List<BookEntity>
}