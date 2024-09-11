package com.example.otchallenge.ui.bookslist.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.otchallenge.di.IODispatcher
import com.example.otchallenge.di.MainDispatcher
import com.example.otchallenge.ui.bookslist.view.BookListView
import com.example.otchallenge.usecase.BooksListUseCaseResult
import com.example.otchallenge.usecase.IBooksListUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class BooksListPresenter @Inject constructor(
    private val useCase: IBooksListUseCase,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) : IBooksListPresenter, CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = mainDispatcher

    private val _uiState = MutableLiveData<BooksListUiState>(BooksListUiState.Idle)
    override val uiState: LiveData<BooksListUiState> get() = _uiState

    override fun onViewAttached(view: BookListView) = getBooks()

    /**
     * we can use [ioDispatcher] in case of blocking work, as needed.
     */
    override fun getBooks() {
        launch {
            _uiState.postValue(BooksListUiState.Loading)

            /**
             * purely for visual appeal and ui testing during configuration changes.
             * feel free to remove.
             */
            delay(2000)

            _uiState.postValue(useCase.getBooks().toUiState())
        }
    }

    private fun BooksListUseCaseResult.toUiState(): BooksListUiState = when (this) {
        BooksListUseCaseResult.Empty -> BooksListUiState.Empty()
        BooksListUseCaseResult.Error -> BooksListUiState.Error
        is BooksListUseCaseResult.CachedList -> BooksListUiState.OfflineList(items)
        is BooksListUseCaseResult.FreshList -> BooksListUiState.OnlineList(items)
    }

    override fun onViewDetached() {
        coroutineContext.cancel()
    }
}