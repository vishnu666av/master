package com.example.otchallenge.data

import com.example.otchallenge.data.network.NYTimesApiService
import com.example.otchallenge.model.Fiction
import javax.inject.Inject

class RemoteFictionsRepository @Inject constructor(private val apiService: NYTimesApiService) :
    Repository<Fiction> {

    override suspend fun all(): List<Fiction> =
        apiService.getAllBooks().results.books.map { Fiction.fromBook(it) }
}