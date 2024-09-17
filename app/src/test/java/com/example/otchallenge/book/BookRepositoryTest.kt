package com.example.otchallenge.book

import com.example.otchallenge.mock.BookTestFactory
import com.example.otchallenge.network.service.ApiResult
import com.example.otchallenge.network.service.BookService
import com.example.otchallenge.repository.BookRepository
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.Response

class BookRepositoryTest {

    private val bookService: BookService = mock()
    private val bookRepository = BookRepository(bookService)

    @Test
    fun getBooks_shouldReturnSuccess() = runBlocking {
        val mockResponse = BookTestFactory.mockBooksResponse()
        `when`(
            bookService.getBooks(anyString(), anyInt())
        ).thenReturn(
            Response.success(mockResponse)
        )

        val result = bookRepository.getBooks(offset = 0)

        assertTrue(result is ApiResult.Success && result.data == mockResponse)
    }

    @Test
    fun getBooks_shouldReturnError() = runBlocking {
        val errorCode = 401
        val errorMessage = "Some Error"
        `when`(
            bookService.getBooks(anyString(), anyInt())
        ).thenReturn(
            Response.error(errorCode, errorMessage.errorResponseBody())
        )

        val result = bookRepository.getBooks(offset = 0)

        assertTrue(result is ApiResult.Error && result.message == errorMessage)
    }

    private fun String.errorResponseBody(): ResponseBody {
        return ResponseBody.create(MediaType.parse("application/json"), this)
    }
}