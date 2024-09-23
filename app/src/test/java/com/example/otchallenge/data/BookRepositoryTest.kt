package com.example.otchallenge.data

import com.example.otchallenge.api.NycApiService
import com.example.otchallenge.bookResponse
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.clearInvocations
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions

class BookRepositoryTest {

    @Test
    fun `getBooks returns cached results when available`() = runTest {
        val mockApiService = mock<NycApiService> {
            onBlocking { getHardcoverFictionBooks(any(), any()) } doReturn bookResponse
        }
        val repository = BookRepository(mockApiService)
        repository.getBooks(0)
        verify(mockApiService, times(1)).getHardcoverFictionBooks(any(), any())
        repository.getBooks(0)
        clearInvocations(mockApiService)
        verifyNoInteractions(mockApiService)
    }

    @Test
    fun `getBooks fetches new data when offset is beyond cache`() = runTest {
        val mockApiService = mock<NycApiService> {
            onBlocking { getHardcoverFictionBooks(any(), any()) } doReturn bookResponse
        }
        val repository = BookRepository(mockApiService)
        repository.getBooks(0)
        verify(mockApiService, times(1)).getHardcoverFictionBooks(any(), any())
        repository.getBooks(20)
        verify(mockApiService, times(2)).getHardcoverFictionBooks(any(), any())
    }
}