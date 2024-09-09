package com.example.otchallenge.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.otchallenge.data.database.BookDao
import com.example.otchallenge.data.mediator.BookPagingMediator
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class BookRepository
    @Inject
    constructor(
        private val mediator: BookPagingMediator,
        private val bookDao: BookDao,
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
