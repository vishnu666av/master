package com.example.otchallenge.ui.bookslist.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.otchallenge.ui.bookslist.view.BookListView
import com.example.otchallenge.usecase.BooksListUseCaseResult
import com.example.otchallenge.usecase.IBooksListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BooksListPresenter @Inject constructor(
    private val useCase: IBooksListUseCase
) : IBooksListPresenter {

    private val _uiState = MutableLiveData<BooksListUiState>(BooksListUiState.Idle)
    override val uiState: LiveData<BooksListUiState> get() = _uiState

    override fun onViewAttached(view: BookListView) {
        // no op
    }

    override suspend fun getBooks() {
        withContext(Dispatchers.IO) {
            _uiState.postValue(BooksListUiState.Loading)

            /**
             * put for visual appeal and ui testing during configuration changes.
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
}