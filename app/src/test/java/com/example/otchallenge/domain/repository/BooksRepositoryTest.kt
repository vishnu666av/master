package com.example.otchallenge.domain.repository

import com.example.otchallenge.data.network.BooksApi
import com.example.otchallenge.domain.errors.AppError
import com.example.otchallenge.domain.testdata.NetworkTestData
import com.example.otchallenge.domain.testdata.PresentationTestData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody.Companion.toResponseBody
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response

class BooksRepositoryTest {

    private val api: BooksApi = mock()

    private val testDispatcher = StandardTestDispatcher()
    private val repository = BooksRepository(
        booksApi = api,
        dispatcher = testDispatcher
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher = testDispatcher)
    }

    @Test
    fun `test fetchDefaultList gets the list mapped to presentation models`() = runTest {
        whenever(api.fetchCurrentList()).doReturn(Response.success(NetworkTestData.buildNetList()))

        val result = repository.fetchDefaultList()

        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo(PresentationTestData.buildList())
    }

    @Test
    fun `test fetchDefaultList rethrows errors`() = runTest {
        whenever(api.fetchCurrentList()).doReturn(
            Response.error(
                400,
                "Error occurred".toResponseBody()
            )
        )

        val result = repository.fetchDefaultList()

        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isInstanceOf(AppError.Http::class.java)
    }

}
