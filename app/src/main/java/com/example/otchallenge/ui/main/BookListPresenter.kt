package com.example.otchallenge.ui.main

import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.otchallenge.data.database.BookEntity
import com.example.otchallenge.data.repository.BookRepository
import com.example.otchallenge.di.AppDispatchers
import com.example.otchallenge.di.ApplicationScope
import com.example.otchallenge.di.Dispatcher
import com.example.otchallenge.ui.components.NetworkMonitor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class BookListPresenter
    @Inject
    constructor(
        private val repository: BookRepository,
        @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
        @ApplicationScope private val appScope: CoroutineScope,
        private val networkMonitor: NetworkMonitor,
    ) : BookListContract.BookListPresenter {
        private var view: BookListContract.BookListView? = null
        override val pagingDataFlow: Flow<PagingData<BookEntity>> = repository.fetchPagedData().cachedIn(appScope)

        override fun fetchBooks(page: Int) {
            view?.showLoader()
            appScope.launch {
            repository
                .fetchPagedData()
                /*.map { pagingData ->
                    pagingData.map { it.toModel() }
                }*/
                .cachedIn(appScope)
                .flowOn(ioDispatcher)
                .map {
                    view?.onLoadBooks(it)

                    view?.hideLoader()
                }.catch {
                    it.printStackTrace()
                    view?.hideLoader()
                    view?.onError(it)
                    }.collect()
                }
        }

        override fun attachView(view: BookListContract.BookListView) {
            if (this.view == null) {
                this.view = view
            }
        }

        override fun detachView() {
            if (view != null) {
                view = null
            }
        }
    }
