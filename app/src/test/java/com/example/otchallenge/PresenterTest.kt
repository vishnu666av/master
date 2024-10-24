package com.example.otchallenge

import com.example.otchallenge.booklist.BookListPresenter
import com.example.otchallenge.booklist.BookListUseCase
import com.example.otchallenge.booklist.BookListView
import com.example.otchallenge.booklist.uistate.BookUIState
import com.example.otchallenge.booklist.uistate.BooksContentUIState
import com.example.otchallenge.booklist.uistate.LoadingState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class PresenterTest {

    private lateinit var mockView: BookListView
    private lateinit var mockUseCase: BookListUseCase
    private lateinit var presenter: BookListPresenter

    @OptIn(ExperimentalCoroutinesApi::class)
    private val dispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        // ** Configure the view
        mockView = mock<BookListView>()
        whenever(mockView.ownerLifecycleScope).thenReturn(CoroutineScope(dispatcher))

        // ** Configure the use case
        mockUseCase = mock<BookListUseCase>()

        // ** Configure the presenter
        presenter = BookListPresenter(mockView, mockUseCase)

        Dispatchers.setMain(dispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should show loading when fetching books`() = runTest {
        // ** Setup the use case
        whenever(mockUseCase.invoke()).thenReturn(BooksContentUIState.EMPTY.copy(loadingState = LoadingState.Ready))
        presenter.loadBooks()

        val expectedState = BooksContentUIState.EMPTY.copy(loadingState = LoadingState.Loading)
        verify(mockView).deliverState(expectedState)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should show error when fetching books fails`() = runTest {
        whenever(mockUseCase.invoke()).thenThrow(RuntimeException::class.java)
        presenter.loadBooks()

        // ** Wait for the coroutine to complete
        advanceUntilIdle()
        assert(presenter.uiState.loadingState is LoadingState.Error && (presenter.uiState.loadingState as LoadingState.Error).throwable is RuntimeException)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should show books when fetching books is successful`() = runTest {
        val books = listOf(
            BookUIState("Title 1", "Ahmad Daneshvar", "Nice book", "https://picsum.photos/200/300", 1),
            BookUIState("Title 2", "Ahmad Daneshvar", "Nice book2", "https://picsum.photos/200/300", 2),
            BookUIState("Title 3", "Ahmad Daneshvar", "Nice book3", "https://picsum.photos/200/300", 3),
        )
        whenever(mockUseCase.invoke()).thenReturn(BooksContentUIState.EMPTY.copy(books))
        presenter.loadBooks()

        advanceUntilIdle()
        assert(presenter.uiState.loadingState is LoadingState.Ready)
        assert(presenter.uiState.books == books)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should retry fetching books when an error occurs`() = runTest {

        // ** Try to retry
        val books = listOf(
            BookUIState("Title 1", "Ahmad Daneshvar", "Nice book", "https://picsum.photos/200/300", 1),
            BookUIState("Title 2", "Ahmad Daneshvar", "Nice book2", "https://picsum.photos/200/300", 2),
            BookUIState("Title 3", "Ahmad Daneshvar", "Nice book3", "https://picsum.photos/200/300", 3),
        )

        // ** Simulate the error state
        whenever(mockUseCase.invoke())
            .thenThrow(RuntimeException::class.java) // <- ** First call that throws an error
            .thenReturn(BooksContentUIState.EMPTY.copy(books)) // <- ** Second call that returns the books

        presenter.loadBooks()
        advanceUntilIdle()
        assert(presenter.uiState.loadingState is LoadingState.Error)

        presenter.reloadIfNeeded()
        advanceUntilIdle()

        assert(presenter.uiState.loadingState is LoadingState.Ready)
        assert(presenter.uiState.books == books)
    }

}