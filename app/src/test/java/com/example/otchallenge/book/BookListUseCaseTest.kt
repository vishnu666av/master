package com.example.otchallenge.book

import com.example.otchallenge.domain.PageStateOption
import com.example.otchallenge.mock.BookTestFactory.mockBooksResponse
import com.example.otchallenge.network.model.BooksResponse
import com.example.otchallenge.network.service.ApiResult
import com.example.otchallenge.repository.BookRepository
import com.example.otchallenge.ui.book.usecase.BookListUseCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyInt

class BookListUseCaseTest {

    private val bookRepository: BookRepository = mock()
    private val bookListUseCase = BookListUseCase(bookRepository)

    @Test
    fun fetchBooks_shouldReturnSuccessState() = runBlocking {
        val mockResponse = mockBooksResponse()
        `when`(
            bookRepository.getBooks(anyInt())
        ).thenReturn(
            ApiResult.Success(mockResponse)
        )

        val result = bookListUseCase.fetchBooks(
            pageStateOption = PageStateOption.DEFAULT,
            offset = 0
        )

        assertTrue(result.items.isNotEmpty())
    }

    @Test
    fun fetchBooks_shouldReturnErrorState() = runBlocking {
        val errorMessage = "Error fetching Books"
        `when`(
            bookRepository.getBooks(anyInt())
        ).thenReturn(
            ApiResult.Error(errorMessage)
        )

        val result = bookListUseCase.fetchBooks(
            pageStateOption = PageStateOption.ERROR,
            offset = 0
        )

        assertEquals(errorMessage, result.userMessage)
    }

    @Test
    fun fetchBooks_shouldReturnEmptyState() = runBlocking {
        val mockResponse = BooksResponse()
        `when`(
            bookRepository.getBooks(anyInt())
        ).thenReturn(
            ApiResult.Success(mockResponse)
        )

        val result = bookListUseCase.fetchBooks(
            pageStateOption = PageStateOption.EMPTY,
            offset = 0
        )

        assertTrue(result.items.isEmpty())
    }

    @Test
    fun fetchBooks_shouldReturnLoadingState() = runBlocking {
        val mockResponse = mockBooksResponse()
        `when`(
            bookRepository.getBooks(anyInt())
        ).thenReturn(
            ApiResult.Success(mockResponse)
        )

        val result = bookListUseCase.fetchBooks(
            pageStateOption = PageStateOption.LOADING,
            offset = 0
        )

        assertEquals(mockResponse.results?.books?.size, result.items.size)
    }
}