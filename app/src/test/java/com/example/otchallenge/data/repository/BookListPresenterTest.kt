package com.example.otchallenge.data.repository

import androidx.paging.PagingData
import com.example.otchallenge.data.database.BookEntity
import com.example.otchallenge.ui.components.NetworkMonitor
import com.example.otchallenge.ui.main.BookListContract
import com.example.otchallenge.ui.main.BookListPresenter
import com.example.otchallenge.ui.main.BookListView
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BookListPresenterTest {
    private val networkMonitor = mockk<NetworkMonitor>(relaxed = true)
    private val repository = mockk<BookRepository>(relaxed = true)
    private val view = mockk<BookListView>(relaxed = true)
    private lateinit var presenter: BookListContract.BookListPresenter

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        presenter =
            BookListPresenter(repository, UnconfinedTestDispatcher(), TestScope(), networkMonitor)
        presenter.attachView(view)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
        presenter.detachView()
    }

    @Test
    fun `return success when first page is fetched`() =
        runTest {
            val pagingData =
                PagingData.from(
                    listOf(
                        BookEntity(
                            1,
                            "dummy isbn",
                            "some long title",
                            "some description",
                            "some author",
                            null,
                        ),
                        BookEntity(
                            2,
                            "dummy 2 isbn",
                            "another long title",
                            "some description",
                            "some author",
                            null,
                        ),
                    ),
                )
            every { repository.fetchPagedData() } returns flowOf(pagingData)
            presenter.fetchBooks(0)
            advanceUntilIdle()
            verify { view.onLoadBooks(any()) }
            verify { view.hideLoader() }
        }

    @Test
    fun `return error when an error occurs from repository`() =
        runTest(UnconfinedTestDispatcher()) {
            every { repository.fetchPagedData() } throws IllegalStateException()
            presenter.fetchBooks(0)
            advanceUntilIdle()
            verify { view.onError(any()) }
            verify { view.hideLoader() }
        }
}
