package com.example.otchallenge.ui.main

import androidx.paging.cachedIn
import com.example.otchallenge.data.repository.BookRepository
import com.example.otchallenge.di.AppDispatchers
import com.example.otchallenge.di.ApplicationScope
import com.example.otchallenge.di.Dispatcher
import com.example.otchallenge.ui.components.NetworkMonitor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
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

        override fun listenToNetworkState() {
            appScope.launch {
                networkMonitor.isOnline.map {
                    view?.onNetworkStateChanged(it)
                }.collect()
            }
        }

        override suspend fun fetchBooks(page: Int) {
            view?.showLoader()
            appScope.launch {
                try {
                    repository
                        .fetchPagedData()
                        .cachedIn(appScope)
                        .flowOn(ioDispatcher)
                        .map {
                            view?.onLoadBooks(it)
                            view?.hideLoader()
                        }.collect()
                } catch (e: Throwable) {
                    e.printStackTrace()
                    view?.hideLoader()
                    view?.onError(e)
                }
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
