package com.example.otchallenge.data

import com.example.otchallenge.data.local.BookDao
import com.example.otchallenge.model.Fiction
import javax.inject.Inject

class LocalFictionsRepository @Inject constructor(private val bookDao: BookDao) :
    Repository<Fiction> {

    override suspend fun all(): List<Fiction> = bookDao.getAllBooks().map { Fiction.fromBook(it) }
}