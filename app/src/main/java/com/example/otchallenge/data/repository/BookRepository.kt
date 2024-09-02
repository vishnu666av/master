package com.example.otchallenge.data.repository

import com.example.otchallenge.data.models.Book
import com.example.otchallenge.data.network.BookDataSource
import com.example.otchallenge.di.AppDispatchers
import com.example.otchallenge.di.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookRepository @Inject constructor(
    private val dataSource: BookDataSource,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) {
    suspend fun fetchBooks(page: Int): Flow<List<Book>> = dataSource.fetchBooks(page)
}
