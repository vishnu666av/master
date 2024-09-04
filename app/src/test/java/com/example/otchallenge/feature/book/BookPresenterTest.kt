package com.example.otchallenge.feature.book

import com.example.otchallenge.domain.BaseResult
import com.example.otchallenge.domain.useCase.getBooks.GetBooksUseCase
import com.example.otchallenge.presentation.bookList.BookListContract
import com.example.otchallenge.presentation.bookList.BookListPresenter
import com.example.otchallenge.presentation.bookList.model.toItemModel
import com.example.otchallenge.util.mockBookDomainList
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class BookPresenterTest {

    @Mock
    private lateinit var mockView: BookListContract.View

    @Mock
    private lateinit var mockGetBooksUseCase: GetBooksUseCase

    private lateinit var presenter: BookListPresenter

    // For testing coroutines
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {

        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)

        presenter = BookListPresenter(mockGetBooksUseCase)
        presenter.attachView(mockView)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        presenter.detachView()
    }

    @Test
    fun testLoadBooksSuccess() = runTest {
        val books = mockBookDomainList()
        `when`(mockGetBooksUseCase.invoke()).thenReturn(BaseResult.Success(books))

        presenter.loadBooks()

        verify(mockView).showLoading()
        verify(mockView).hideLoading()
        verify(mockView).showBooks(books.map { it.toItemModel() })
    }

    @Test
    fun testAttachDetachView() {
        presenter.attachView(mockView)
        assertNotNull(presenter.getView())

        presenter.detachView()
        assert(presenter.getView() == null)
    }

    @Test
    fun testLoadBooksFailure() = runTest {
        val error = RuntimeException("Error")
        `when`(mockGetBooksUseCase.invoke()).thenReturn(BaseResult.Error(error))

        presenter.loadBooks()

        verify(mockView).showLoading()
        verify(mockView).hideLoading()
        verify(mockView).showError("Error")
    }

}