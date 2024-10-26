package com.example.otchallenge.presenter

import android.content.Context
import com.example.otchallenge.api.BookService
import com.example.otchallenge.model.BookResponse
import com.example.otchallenge.presenter.BookDetailView
import com.example.otchallenge.presenter.BookListPresenter
import com.example.otchallenge.presenter.BookListView
import com.google.gson.Gson
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.concurrent.TimeUnit


class BookListPresenterTest {

    @Mock
    private lateinit var bookService: BookService

    @Mock
    private lateinit var bookListView: BookListView

    @Mock
    private lateinit var bookDetailView: BookDetailView

    @Mock
    private lateinit var context: Context

    private lateinit var bookListPresenter: BookListPresenter
    private lateinit var testScheduler: TestScheduler

    private val jsonResponse = "{\"status\":\"OK\",\"copyright\":\"Copyright (c) 2024 The New York Times Company.  All Rights Reserved.\",\"num_results\":15,\"last_modified\":\"2024-10-16T22:04:46-04:00\",\"results\":{\"list_name\":\"Hardcover Fiction\",\"list_name_encoded\":\"hardcover-fiction\",\"bestsellers_date\":\"2024-10-12\",\"published_date\":\"2024-10-27\",\"published_date_description\":\"latest\",\"next_published_date\":\"\",\"previous_published_date\":\"2024-10-20\",\"display_name\":\"Hardcover Fiction\",\"normal_list_ends_at\":15,\"updated\":\"WEEKLY\",\"books\":[{\"rank\":1,\"rank_last_week\":1,\"weeks_on_list\":3,\"asterisk\":0,\"dagger\":0,\"primary_isbn10\":\"0593449592\",\"primary_isbn13\":\"9780593449592\",\"publisher\":\"Random House\",\"description\":\"A man in search of the father he never knew encounters a single mom and rumors circulate of the nearby appearance of a white deer.\",\"price\":\"0.00\",\"title\":\"COUNTING MIRACLES\",\"author\":\"Nicholas Sparks\",\"contributor\":\"by Nicholas Sparks\",\"contributor_note\":\"\",\"book_image\":\"https:\\/\\/storage.googleapis.com\\/du-prd\\/books\\/images\\/9780593449592.jpg\",\"book_image_width\":330,\"book_image_height\":500,\"amazon_product_url\":\"https:\\/\\/www.amazon.com\\/dp\\/0593449592?tag=thenewyorktim-20\",\"age_group\":\"\",\"book_review_link\":\"\",\"first_chapter_link\":\"\",\"sunday_review_link\":\"\",\"article_chapter_link\":\"\",\"isbns\":[{\"isbn10\":\"0593449592\",\"isbn13\":\"9780593449592\"},{\"isbn10\":\"0593449606\",\"isbn13\":\"9780593449608\"}],\"buy_links\":[{\"name\":\"Amazon\",\"url\":\"https:\\/\\/www.amazon.com\\/dp\\/0593449592?tag=thenewyorktim-20\"},{\"name\":\"Apple Books\",\"url\":\"https:\\/\\/goto.applebooks.apple\\/9780593449592?at=10lIEQ\"},{\"name\":\"Barnes and Noble\",\"url\":\"https:\\/\\/www.anrdoezrs.net\\/click-7990613-11819508?url=https%3A%2F%2Fwww.barnesandnoble.com%2Fw%2F%3Fean%3D9780593449592\"},{\"name\":\"Books-A-Million\",\"url\":\"https:\\/\\/www.anrdoezrs.net\\/click-7990613-35140?url=https%3A%2F%2Fwww.booksamillion.com%2Fp%2FCOUNTING%2BMIRACLES%2FNicholas%2BSparks%2F9780593449592\"},{\"name\":\"Bookshop.org\",\"url\":\"https:\\/\\/bookshop.org\\/a\\/3546\\/9780593449592\"}],\"book_uri\":\"nyt:\\/\\/book\\/974caeee-6bf0-524c-809c-29bfdd99d65f\"}],\"corrections\":[]}}"

    val apiKey = "KoRB4K5LRHygfjCL2AH6iQ7NeUqDAGAB"

    @Before
    fun setup(){
        MockitoAnnotations.openMocks(this)
        bookListPresenter = BookListPresenter(bookService)
        bookListPresenter.attachListView(bookListView)
        bookListPresenter.attachDetailView(bookDetailView)
        testScheduler = TestScheduler()
    }

    @After
    fun tearDown() {
        bookListPresenter.detachView()
    }

    @Test
    fun `fetchBooks should show books on successful response`(){
        val apiResponse = Gson().fromJson(jsonResponse, BookResponse::class.java)
        val bookList = apiResponse.results.books

        whenever(bookService.getBooks(apiKey, 0, context)).thenReturn(Single.just(bookList))

        bookListPresenter.fetchBooks(apiKey, 0, context)

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)

        verify(bookListView).onBooksFetched(bookList)
    }

    @Test
    fun `fetchBook should call showError on failure response`(){
        val errorMessage = "Network error"
        `when`(bookService.getBooks(apiKey, 0, context)).thenReturn(Single.error(Throwable(errorMessage)))
        bookListPresenter.fetchBooks(apiKey, 0, mock(Context::class.java))

        verify(bookListView).showError(errorMessage)
        verify(bookListView, never()).onBooksFetched(any())
    }

}