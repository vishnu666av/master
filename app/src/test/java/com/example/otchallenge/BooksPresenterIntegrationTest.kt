package com.example.otchallenge

import com.example.otchallenge.dao.BookDao
import com.example.otchallenge.model.Book
import com.example.otchallenge.presenter.BooksContract
import com.example.otchallenge.presenter.BooksPresenter
import com.example.otchallenge.repository.BookRepository
import com.example.otchallenge.libs.ApiService
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.mockito.Mockito.*
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.net.HttpURLConnection

class BooksPresenterIntegrationTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: ApiService
    private lateinit var repository: BookRepository
    private lateinit var presenter: BooksPresenter
    private lateinit var view: BooksContract.View
    private lateinit var bookDao: BookDao

    @Before
    fun setUp() {
        // Initialize MockWebServer
        mockWebServer = MockWebServer()
        mockWebServer.start()

        // Create ApiService with Retrofit
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))  // Mock 서버를 사용
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(ApiService::class.java)

        bookDao = mock(BookDao::class.java)

        // Create repository with the mock ApiService
        repository = BookRepository(apiService, bookDao)

        // Mock the view
        view = mock(BooksContract.View::class.java)

        presenter = BooksPresenter(repository)

        // Set up schedulers for RxJava
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun fetchBooks_success() {
        // Prepare mock response
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("""
                [
                    {
                        "isbn": "9781250289568",
                        "id": 14,
                        "title": "PASSIONS IN DEATH",
                        "author": "J.D. Robb",
                        "description": "The 59th book of the In Death series. Bad memories come up for Eve Dallas at a crime scene.",
                        "imageUrl": "https://storage.googleapis.com/du-prd/books/images/9781250289568.jpg"
                    },
                    {
                        "isbn": "9780385550369",
                        "id": 15,
                        "title": "JAMES",
                        "author": "Percival Everett",
                        "description": "A reimagining of Adventures of Huckleberry Finn shines a different light on Mark Twain's classic.",
                        "imageUrl": "https://storage.googleapis.com/du-prd/books/images/9780385550369.jpg"
                    }
                ]
            """.trimIndent())

        mockWebServer.enqueue(mockResponse)

        // Attach the view to the presenter
        presenter.attach(view)

        // Call fetchBooks method
        presenter.fetchBooks()

        // Verify the expected interactions
        verify(view).showProgress()

        // Verify that the view shows the correct book data
        val expectedBooks = listOf(
            Book(
                "9781250289568", 14, "PASSIONS IN DEATH", "J.D. Robb",
                "The 59th book of the In Death series. Bad memories come up for Eve Dallas at a crime scene.",
                "https://storage.googleapis.com/du-prd/books/images/9781250289568.jpg"
            ),
            Book(
                "9780385550369", 15, "JAMES", "Percival Everett",
                "A reimagining of Adventures of Huckleberry Finn shines a different light on Mark Twain's classic.",
                "https://storage.googleapis.com/du-prd/books/images/9780385550369.jpg"
            )
        )
        verify(view).displayBooks(expectedBooks)
        verify(view).hideProgress()

        // Ensure no error is shown
        verify(view, never()).showError(anyString())
    }

    @After
    fun tearDown() {
        // Shut down the mock server
        mockWebServer.shutdown()

        // Reset RxJava plugins
        RxAndroidPlugins.reset()
        RxJavaPlugins.reset()
    }
}
