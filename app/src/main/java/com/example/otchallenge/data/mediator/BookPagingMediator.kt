package com.example.otchallenge.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.otchallenge.data.database.AppDatabase
import com.example.otchallenge.data.database.BookEntity
import com.example.otchallenge.data.database.RemoteKey
import com.example.otchallenge.data.models.toBookEntity
import com.example.otchallenge.data.network.BookDataSource
import com.example.otchallenge.di.AppDispatchers
import com.example.otchallenge.di.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class BookPagingMediator
    @Inject
    constructor(
        private val appDb: AppDatabase,
//        private val bookDao: BookDao,
//        private val remoteKeyDao: RemoteKeyDao,
        private val dataSource: BookDataSource,
        @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    ) : RemoteMediator<Int, BookEntity>() {
        override suspend fun initialize(): InitializeAction {
            val remoteKey =
                appDb.withTransaction {
                    appDb.remoteKeyDao().getKeyById("last_book")
                } ?: return InitializeAction.LAUNCH_INITIAL_REFRESH

            val cacheTimeout = TimeUnit.MINUTES.convert(10, TimeUnit.MILLISECONDS)

            return if ((System.currentTimeMillis() - remoteKey.lastUpdated) >= cacheTimeout) {
                InitializeAction.LAUNCH_INITIAL_REFRESH
            } else {
                InitializeAction.SKIP_INITIAL_REFRESH
            }
        }

        override suspend fun load(
            loadType: LoadType,
            state: PagingState<Int, BookEntity>,
        ): MediatorResult {
            return try {
                val page =
                    when (loadType) {
                        LoadType.REFRESH -> {
                            0
                        }

                        LoadType.PREPEND -> {
                            return MediatorResult.Success(true)
                        }

                        LoadType.APPEND -> {
                            val remoteKey =
                                appDb.withTransaction {
                                    appDb.remoteKeyDao().getKeyById("last_book")
                                }
                                    ?: return MediatorResult.Success(true)

                            if (remoteKey.nextPage == null) {
                                return MediatorResult.Success(true)
                            }

                            remoteKey.nextPage
                        }
                    }
                val result =
                    dataSource.fetchBooks(page)
                appDb.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        appDb.bookDao().nukeTable()
                        appDb.remoteKeyDao().clearKeys()
                    }

                    val nextPage =
                        if (result.isEmpty()) {
                            null
                        } else {
                            page + 1
                        }

                    val bookEntities =
                        result.map {
                            it.toBookEntity()
                        }

                    appDb.remoteKeyDao().insertKey(
                        RemoteKey(
                            id = "last_book",
                            nextPage = nextPage,
                            lastUpdated = System.currentTimeMillis(),
                        ),
                    )
                    appDb.bookDao().insertAllBooks(bookEntities)
                }
                MediatorResult.Success(
                    endOfPaginationReached = result.isEmpty(),
                )
            } catch (e: Exception) {
                e.printStackTrace()
                MediatorResult.Error(e)
            }
        }
    }
