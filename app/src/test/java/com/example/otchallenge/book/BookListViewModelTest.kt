package com.example.otchallenge.book

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.otchallenge.domain.BookListViewState
import com.example.otchallenge.domain.PageStateOption
import com.example.otchallenge.mock.BookTestFactory.mockBookUIList
import com.example.otchallenge.ui.book.usecase.BookListUseCase
import com.example.otchallenge.ui.book.viewmodel.BookListViewModel
import com.example.otchallenge.util.NetworkUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class BookListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    private val bookListUseCase: BookListUseCase = mock()
    private val networkUtil: NetworkUtil = mock()

    private lateinit var viewModel: BookListViewModel

    private val offset = 0

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = BookListViewModel(bookListUseCase, networkUtil)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun observeStates(testBlock: () -> Unit): List<BookListViewState> {
        val observedStates = mutableListOf<BookListViewState>()
        viewModel.uiState.observeForever { observedStates.add(it) }
        testBlock()
        return observedStates
    }

    @Test
    fun fetchBooks_shouldEmitLoadingState() = runBlocking {
        val loadingState = BookListViewState(isLoading = true)
        `when`(
            bookListUseCase.fetchBooks(pageStateOption = PageStateOption.DEFAULT, offset = offset)
        ).thenReturn(
            loadingState
        )

        val observedStates = observeStates {
            viewModel.fetchBooks(PageStateOption.DEFAULT)
        }

        assert(observedStates.first().isLoading)
    }

    @Test
    fun fetchBooks_shouldEmitSuccessState() = runBlocking {
        val successState = BookListViewState(items = mockBookUIList())
        `when`(
            bookListUseCase.fetchBooks(PageStateOption.DEFAULT, offset)
        ).thenReturn(
            successState
        )

        val observedStates = observeStates {
            viewModel.fetchBooks(PageStateOption.DEFAULT)
        }

        assert(observedStates.last().items.isNotEmpty())
        assert(observedStates.last().items == successState.items)
    }

    @Test
    fun fetchBooks_shouldEmitEmptyState() = runBlocking {
        val emptyState = BookListViewState(items = emptyList())
        `when`(bookListUseCase.fetchBooks(PageStateOption.EMPTY, offset)).thenReturn(emptyState)

        val observedStates = observeStates {
            viewModel.fetchBooks(PageStateOption.EMPTY)
        }

        assert(observedStates.last().items.isEmpty())
    }

    @Test
    fun fetchBooks_shouldEmitErrorState() = runBlocking {
        val errorMessage = "Error fetching books"
        `when`(
            bookListUseCase.fetchBooks(PageStateOption.ERROR, offset)
        ).thenReturn(
            BookListViewState(userMessage = errorMessage)
        )

        val observedStates = observeStates {
            viewModel.fetchBooks(PageStateOption.ERROR)
        }

        assert(observedStates.last().userMessage == errorMessage)
    }

    @Test
    fun refreshBooks_shouldEmitSuccessState() = runBlocking {
        val successState = BookListViewState(items = mockBookUIList())
        `when`(
            bookListUseCase.fetchBooks(
                pageStateOption = PageStateOption.DEFAULT,
                offset = offset
            )
        ).thenReturn(successState)

        val observedStates = observeStates {
            viewModel.refreshBookList()
        }

        assert(observedStates.last().items.isNotEmpty())
        assert(observedStates.last().items == successState.items)
    }

    @Test
    fun refreshBooks_shouldEmitEmptyState() = runBlocking {
        val emptyState = BookListViewState(items = emptyList())
        `when`(
            bookListUseCase.fetchBooks(
                pageStateOption = PageStateOption.DEFAULT,
                offset = offset
            )
        ).thenReturn(emptyState)

        val observedStates = observeStates {
            viewModel.refreshBookList()
        }

        assert(observedStates.last().items.isEmpty())
    }
}