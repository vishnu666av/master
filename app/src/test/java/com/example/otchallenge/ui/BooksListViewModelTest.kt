package com.example.otchallenge.ui

import com.example.otchallenge.data.BookDto
import com.example.otchallenge.fixtures.testPrototype
import com.example.otchallenge.fixtures.testPrototypeList
import com.example.otchallenge.model.BooksListDataState
import com.example.otchallenge.model.BooksListPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`

@OptIn(ExperimentalCoroutinesApi::class)
class BooksListViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var presenter: BooksListPresenter

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        presenter = Mockito.spy(BooksListPresenter::class.java)
    }

    @Test
    fun when_presenter_empty_then_uiState_empty() = runTest(testDispatcher) {
        // Arrange
        `when`(presenter.getBooks()).thenReturn(BooksListDataState.Empty(timestamp = ""))

        val viewModel = BooksListViewModel(presenter)

        // Act
        var state: BooksListUiState? = null
        val job = launch {
            viewModel.uiState.collectLatest { state = it }
        }

        advanceUntilIdle()
        job.cancel()

        // Assert
        assert(state != null)
        assert(state is BooksListUiState.Empty)
    }

    @Test
    fun when_presenter_error_then_uiState_error() = runTest(testDispatcher) {
        // Arrange
        `when`(presenter.getBooks()).thenReturn(
            BooksListDataState.Error(
                message = "failure",
                timestamp = ""
            )
        )

        val viewModel = BooksListViewModel(presenter)

        // Act
        var state: BooksListUiState? = null
        val job = launch {
            viewModel.uiState.collectLatest { state = it }
        }

        advanceUntilIdle()
        job.cancel()

        // Assert
        assert(state != null)
        assert(state is BooksListUiState.Error)
    }

    @Test
    fun when_presenter_fresh_list_then_uiState_online_list() = runTest(testDispatcher) {
        // Arrange
        `when`(presenter.getBooks()).thenReturn(
            BooksListDataState.FreshList(
                items = BookDto.Companion.testPrototypeList(),
                timestamp = ""
            )
        )

        val viewModel = BooksListViewModel(presenter)

        // Act
        var state: BooksListUiState? = null
        val job = launch {
            viewModel.uiState.collectLatest { state = it }
        }

        advanceUntilIdle()
        job.cancel()

        // Assert
        assert(state != null)
        assert(state is BooksListUiState.OnlineList)
        assert((state as BooksListUiState.OnlineList).items.size == 1)

        val book = (state as BooksListUiState.OnlineList).items[0]
        assert(book.title == BookDto.testPrototype().title)
        assert(book.author == BookDto.testPrototype().author)
        assert(book.description == BookDto.testPrototype().description)
        assert(book.rank == BookDto.testPrototype().rank)
        assert(book.imageUrl == BookDto.testPrototype().imageUrl)
        assert(book.buyLinks == BookDto.testPrototype().buyLinks)
    }

    @Test
    fun when_presenter_stale_list_then_uiState_offline_list() = runTest(testDispatcher) {
        // Arrange
        `when`(presenter.getBooks()).thenReturn(
            BooksListDataState.StaleList(
                items = BookDto.Companion.testPrototypeList(),
                timestamp = ""
            )
        )

        val viewModel = BooksListViewModel(presenter)

        // Act
        var state: BooksListUiState? = null
        val job = launch {
            viewModel.uiState.collectLatest { state = it }
        }

        advanceUntilIdle()
        job.cancel()

        // Assert
        assert(state != null)
        assert(state is BooksListUiState.OfflineList)
        assert((state as BooksListUiState.OfflineList).items.size == 1)

        val book = (state as BooksListUiState.OfflineList).items[0]
        assert(book.title == BookDto.testPrototype().title)
        assert(book.author == BookDto.testPrototype().author)
        assert(book.description == BookDto.testPrototype().description)
        assert(book.rank == BookDto.testPrototype().rank)
        assert(book.imageUrl == BookDto.testPrototype().imageUrl)
        assert(book.buyLinks == BookDto.testPrototype().buyLinks)
    }
}