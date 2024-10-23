package com.example.otchallenge.presenter

import com.example.otchallenge.model.BookModel
import com.example.otchallenge.model.BooksData
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*

class BooksPresenterTests {

    private lateinit var presenter: BooksPresenter
    private lateinit var currentData: BooksData
    @Mock
    lateinit var interfaceMock : BooksPresenterInterface

    @Before
    fun setup() {
        currentData = BooksData()
        interfaceMock = mock()
        presenter = BooksPresenter(interfaceMock)
    }

    @Test
    fun testDefaultInfo() {
        // given default data

        whenPresenterPresentData()

        // then
        verify(interfaceMock, times(0)).displayError(anyString())
        verify(interfaceMock).displayLoader(eq(false))
        verify(interfaceMock).displayBooks(currentData.books)
    }

    @Test
    fun testShouldDisplayLoaderWhenLoading() {
        // given a data that is loading
        currentData = currentData.copy(isLoading = true)

        whenPresenterPresentData()

        // then loader should be displayed
        verify(interfaceMock).displayLoader(eq(true))
    }

    @Test
    fun testShouldDisplayDataWhenBooksAvailable() {
        // given data with books
        val books = listOf(BookModel("Book", "Description"))
        currentData = currentData.copy(books = books)

        whenPresenterPresentData()

        // then verify book is displayed
        verify(interfaceMock).displayLoader(false)
        verify(interfaceMock).displayBooks(books)
        // no error is being invoked
        verify(interfaceMock, times(0)).displayError(anyString())
    }

    @Test
    fun testShouldDisplayError() {
        // given a data that has error
        currentData = currentData.copy(showError = true, errorMessage = "Error")

        whenPresenterPresentData()

        // then error should be displayed
        verify(interfaceMock).displayError("Error")
        // and no other methods should be called
        verify(interfaceMock, times(0)).displayLoader(anyBoolean())
        verify(interfaceMock, times(0)).displayBooks(anyList())
    }

    private fun whenPresenterPresentData() {
        presenter.presentUi(currentData)
    }
}