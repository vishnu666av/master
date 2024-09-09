package com.example.otchallenge.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.otchallenge.data.database.BookDao
import com.example.otchallenge.data.mediator.BookPagingMediator
import com.example.otchallenge.data.network.BookDataSource
import com.example.otchallenge.di.AppDispatchers
import com.example.otchallenge.di.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class BookRepository
    @Inject
    constructor(
        private val mediator: BookPagingMediator,
        private val bookDao: BookDao,
        private val dataSource: BookDataSource,
        @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    ) {
        fun fetchPagedData() =
            Pager(
                config =
                    PagingConfig(
                        pageSize = 20,
                    ),
                remoteMediator = mediator,
                pagingSourceFactory = { bookDao.fetchPagingData() },
            ).flow
    }
