package com.example.otchallenge.model.network

import com.example.otchallenge.model.BookDto
import com.example.otchallenge.model.Repository
import javax.inject.Inject

class RemoteBooksRepository @Inject constructor(private val apiService: NYTimesApiService) :
    Repository<BookDto> {

    override suspend fun all(): List<BookDto> =
        apiService.getAllBooks().results.books.sortedBy { it.rank }

    override suspend fun save(items: List<BookDto>) {
        // no op - the api doesn't support this operation
    }
}