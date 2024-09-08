package com.example.otchallenge.data

import com.example.otchallenge.data.local.BookDao
import javax.inject.Inject

class LocalFictionsRepository @Inject constructor(private val bookDao: BookDao) :
    Repository<BookDto> {

    override suspend fun all(): List<BookDto> = bookDao.getAllBooks()

    override suspend fun save(items: List<BookDto>) = items.forEach { bookDao.insert(it) }
}