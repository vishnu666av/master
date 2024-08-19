package com.example.otchallenge.ui.book.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.otchallenge.R
import com.example.otchallenge.domain.BookListViewState
import com.example.otchallenge.domain.PageStateOption
import com.example.otchallenge.ui.book.usecase.BookListUseCase
import com.example.otchallenge.util.NetworkUtil
import com.example.otchallenge.util.PageState
import com.example.otchallenge.util.PageStateUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for managing the UI state of the book list screen.
 *
 * @property bookListUseCase The use case for fetching and processing book data.
 */
@HiltViewModel
class BookListViewModel @Inject constructor(
    private val bookListUseCase: BookListUseCase,
    private val networkUtil: NetworkUtil,
) : ViewModel() {

    private val offset: Int = 0

    private val _uiState = MutableLiveData<BookListViewState>()
    val uiState: LiveData<BookListViewState> = _uiState

    /**
     * Fetches the list of book and updates the UI state accordingly.
     */
    fun fetchBooks(
        pageStateOption: PageStateOption = PageStateOption.DEFAULT,
        forceRefresh: Boolean = false
    ) {
        // If the book list is already available, do not fetch again
        // When we rotate the device, the UI state is retained and we don't need to fetch again
        if (!forceRefresh && _uiState.value?.items?.isNotEmpty() == true) {
            _uiState.postValue(_uiState.value)
            return
        }

        viewModelScope.launch {
            // Emit a loading state before fetching book
            emitLoadingState()

            val bookListUiState = bookListUseCase.fetchBooks(
                pageStateOption = pageStateOption,
                offset = offset
            )

            _uiState.postValue(bookListUiState)
        }
    }

    private fun emitLoadingState() {
        if (_uiState.value?.isLoading != true) {
            _uiState.postValue(BookListViewState(isLoading = true))
        }
    }

    /**
     * Returns the current page state of the book list.
     */
    fun determinePageState(): PageState? {
        return _uiState.value?.let {
            PageStateUtil.getPageState(
                uiState = it,
                isNetworkConnected = networkUtil.isNetworkConnected()
            )
        }
    }

    /**
     * Sets the page state option for fetching books.
     */
    fun setPageStateOption(menuItemId: Int) {
        val option = when (menuItemId) {
            R.id.action_default -> PageStateOption.DEFAULT
            R.id.action_error -> PageStateOption.ERROR
            R.id.action_loading -> PageStateOption.LOADING
            R.id.action_empty -> PageStateOption.EMPTY
            else -> PageStateOption.DEFAULT
        }
        fetchBooks(pageStateOption = option, forceRefresh = true)
    }

    /**
     * Refreshes the list of books by re-fetching.
     */
    fun refreshBookList() {
        viewModelScope.launch {
            val booksListUiState = bookListUseCase.fetchBooks(offset = offset)
            _uiState.postValue(booksListUiState)
        }
    }
}