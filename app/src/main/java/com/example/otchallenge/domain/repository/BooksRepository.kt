package com.example.otchallenge.domain.repository

import com.example.otchallenge.data.network.BooksApi
import com.example.otchallenge.di.IoDispatcher
import com.example.otchallenge.domain.models.BookList
import com.example.otchallenge.domain.models.toBookList
import com.example.otchallenge.domain.utils.NetworkUtils.toResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BooksRepository @Inject constructor(
    private val booksApi: BooksApi,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend fun fetchDefaultList(): Result<BookList> = withContext(dispatcher) {
        booksApi.fetchCurrentList()
            .toResult()
            .map { it.toBookList() }
    }
}
