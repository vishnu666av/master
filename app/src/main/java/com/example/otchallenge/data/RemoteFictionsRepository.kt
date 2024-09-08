package com.example.otchallenge.data

import com.example.otchallenge.data.network.NYTimesApiService
import javax.inject.Inject

class RemoteFictionsRepository @Inject constructor(private val apiService: NYTimesApiService) :
    Repository<BookDto> {

    override suspend fun all(): List<BookDto> = apiService.getAllBooks().results.books

    override suspend fun save(items: List<BookDto>) {
        // no op - the api doesn't support this operation
    }
}