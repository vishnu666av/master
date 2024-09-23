package com.example.otchallenge.ui

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.example.otchallenge.bookModel
import com.example.otchallenge.data.BookRepository
import com.example.otchallenge.ui.booklist.BookListPresenter
import com.example.otchallenge.ui.booklist.BookListView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.mockito.kotlin.wheneverBlocking

class BookPresenterTest {
    private val mockView = mock<BookListView>()
    private val mockRepository = mock<BookRepository>()
    private val presenter = BookListPresenter(mockRepository).apply {
        attachView(mockView)
    }
    private val mockLifecycleOwner: LifecycleOwner = mock()
    private val lifecycle: LifecycleRegistry = LifecycleRegistry(mockLifecycleOwner)
    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope: TestScope = TestScope(testDispatcher)

    @Before
    fun setup() {
        whenever(mockLifecycleOwner.lifecycle).thenReturn(lifecycle)
        whenever(mockView.getLifecycleScope()).thenReturn(testScope)
    }

    @Test
    fun `loadInitialData shows loading, fetches data, and updates view`() {
        val books = listOf(bookModel.toBook())
        wheneverBlocking {
            mockRepository.getBooks(0)
        }.thenReturn(Result.success(books))
        presenter.loadInitialData()
        Mockito.verify(mockView).showLoading()
        Mockito.verify(mockView).addBooks(books)
        Mockito.verify(mockView).hideLoading()
    }

    @Test
    fun `loadMoreData does not load when already loading`() = runTest {
        presenter.isLoading = true
        presenter.loadMoreData()
        verifyNoMoreInteractions(mockRepository)
        verifyNoMoreInteractions(mockView)
    }

    @Test
    fun `loadMoreData does not load when no more data`() = runTest {
        presenter.hasMoreData = false
        presenter.loadMoreData()

        verifyNoMoreInteractions(mockRepository)
        verifyNoMoreInteractions(mockView)
    }
}