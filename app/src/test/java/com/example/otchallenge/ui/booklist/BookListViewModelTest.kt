package com.example.otchallenge.ui.booklist

import app.cash.turbine.test
import com.example.otchallenge.domain.errors.AppError
import com.example.otchallenge.domain.repository.BooksRepository
import com.example.otchallenge.domain.testdata.PresentationTestData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class BookListViewModelTest {

    private val booksRepository: BooksRepository = mock()

    private val viewModel = BookListViewModel(booksRepository)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @Test
    fun `test state starts with null list`() = runTest{
        viewModel.getState().test {
            val initialEmission = awaitItem()
            assertThat(initialEmission.bookListResult).isNull()
        }
    }

    @Test
    fun `test emits error if repository fails`() = runTest{
        whenever(booksRepository.fetchDefaultList()).thenReturn(
            Result.failure(AppError.Network("This is an error"))
        )
        viewModel.getState().test {
            val initialEmission = awaitItem()
            assertThat(initialEmission.bookListResult).isNull()
            viewModel.fetchBooks()
            val secondEmission = awaitItem()
            assertThat(secondEmission.bookListResult?.isFailure).isTrue()
        }
    }

    @Test
    fun `test recovers from error if repository fails and then succeds`() = runTest{
        whenever(booksRepository.fetchDefaultList()).thenReturn(
            Result.failure(AppError.Network("This is an error"))
        ).thenReturn(
            Result.success(PresentationTestData.buildList())
        )
        viewModel.getState().test {
            val initialEmission = awaitItem()
            assertThat(initialEmission.bookListResult).isNull()
            viewModel.fetchBooks()
            val secondEmission = awaitItem()
            assertThat(secondEmission.bookListResult?.isFailure).isTrue()
            viewModel.fetchBooks()
            val thirdEmission = awaitItem()
            // Returning to loading state
            assertThat(thirdEmission.bookListResult).isNull()
            val fourthEmission = awaitItem()
            assertThat(fourthEmission.bookListResult?.isSuccess).isTrue()
            assertThat(fourthEmission.bookListResult?.getOrNull()).isEqualTo(PresentationTestData.buildList())
        }
    }

    @Test
    fun `test loads list on success`() = runTest{
        whenever(booksRepository.fetchDefaultList()).thenReturn(
            Result.success(PresentationTestData.buildList())
        )
        viewModel.getState().test {
            val initialEmission = awaitItem()
            assertThat(initialEmission.bookListResult).isNull()
            viewModel.fetchBooks()
            val secondEmission = awaitItem()
            assertThat(secondEmission.bookListResult?.isSuccess).isTrue()
            assertThat(secondEmission.bookListResult?.getOrNull()).isEqualTo(PresentationTestData.buildList())
        }
    }
}
