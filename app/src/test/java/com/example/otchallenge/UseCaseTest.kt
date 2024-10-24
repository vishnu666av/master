package com.example.otchallenge

import com.example.otchallenge.api.NYTApiService
import com.example.otchallenge.api.dto.BookDto
import com.example.otchallenge.api.dto.BookResponseDto
import com.example.otchallenge.api.dto.BookResultDto
import com.example.otchallenge.booklist.BookListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import java.time.LocalDateTime

class UseCaseTest {

    @Mock
    private lateinit var nytApiService: NYTApiService

    private lateinit var useCase: BookListUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    private val dispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        useCase = BookListUseCase(nytApiService, dispatcher)

        Dispatchers.setMain(dispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `calls api for data is successful`() = runTest {
        // ** Preparing mock response
        val books = listOf(
            BookDto(
                0,
                "Creative Commons",
                "Mastring Java",
                "James Gosling",
                "...",
                "...",
                "...",
                "..."
            ),
            BookDto(
                1,
                "Awesome works",
                "The art Of Programming",
                "James Gosling",
                "...",
                "...",
                "...",
                "..."
            )
        )

        val resultDto = BookResultDto("Hardcover Fiction", books)
        val responseDto = BookResponseDto(resultDto, "2024, Ahmad Daneshvar", 14, LocalDateTime.now())

        // ** Start test scenario **
        whenever(nytApiService.fetchBooks()).thenReturn(responseDto)
        val content = useCase()

        assert(content == responseDto)
    }
}