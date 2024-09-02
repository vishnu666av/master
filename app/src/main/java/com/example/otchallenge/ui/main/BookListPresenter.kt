package com.example.otchallenge.ui.main

import com.example.otchallenge.data.repository.BookRepository
import com.example.otchallenge.di.AppDispatchers
import com.example.otchallenge.di.ApplicationScope
import com.example.otchallenge.di.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class BookListPresenter
    @Inject
    constructor(
        private val repository: BookRepository,
        @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
        @ApplicationScope private val appScope: CoroutineScope,
    ) : BookListContract.BookListPresenter {
        private var _view: BookListContract.BookListView? = null

        override fun fetchBooks(page: Int) {
            appScope.launch {
                repository
                    .fetchBooks(page)
                    .map {
                        _view?.onLoadBooks(it)
                    }.flowOn(ioDispatcher)
                    .onStart { flowOf(ListResultUiState.Loading) }
                    .catch {
                        it.printStackTrace()
                        _view?.onError(it)
                    }.collect()
            }
        }

        override fun attachView(view: BookListContract.BookListView) {
            if (_view == null) {
                _view = view
            }
        }

        override fun detachView() {
            if (_view != null) {
                _view = null
            }
        }
    }
