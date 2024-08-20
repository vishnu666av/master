package com.example.otchallenge.data.local

//In a production app this would be a Room database
class InMemoryDao: BookDao {

    private var books: List<BookEntity> = emptyList()

    override suspend fun saveAll(books: List<BookEntity>) {
        this.books = books
    }

    override suspend fun getAll(): List<BookEntity> {
        return books
    }
}