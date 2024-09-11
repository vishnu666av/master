package com.example.otchallenge.model.local

import com.example.otchallenge.model.BookDto
import com.example.otchallenge.model.Repository
import javax.inject.Inject

class LocalBooksRepository @Inject constructor(private val bookDao: BookDao) :
    Repository<BookDto> {

    override suspend fun all(): List<BookDto> = bookDao.getAll()

    override suspend fun save(items: List<BookDto>) = bookDao.insert(items)
}