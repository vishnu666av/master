package com.example.otchallenge

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.otchallenge.dao.BookDao
import com.example.otchallenge.libs.ApiService
import com.example.otchallenge.model.ApiResponse
import com.example.otchallenge.model.ApiResults
import com.example.otchallenge.model.Book
import com.example.otchallenge.repository.BookDatabase
import com.example.otchallenge.repository.BookRepository
import io.reactivex.Single
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.MockedConstruction
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class BookRepositoryTest {
    private lateinit var bookRepository: BookRepository
    private lateinit var db: BookDatabase
    private lateinit var bookDao: BookDao

    @Mock
    private lateinit var apiService: ApiService

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        // Create an in-memory Room database
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, BookDatabase::class.java
        ).allowMainThreadQueries().build()

        bookDao = db.bookDao()

        // Use Schedulers.trampoline() to handle async RxJava calls in tests
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }

        bookRepository = BookRepository(apiService, bookDao)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        if (::db.isInitialized) {
            db.close()
        }
    }

    @Test
    fun testFetchBooksFromApiAndStoreInDb() {
        // Mock API response
        val mockBooks = listOf(
            Book(
                "9781250289568",
                14,
                "PASSIONS IN DEATH",
                "J.D. Robb",
                "The 59th book of the In Death series. Bad memories come up for Eve Dallas at a crime scene.",
                "https://storage.googleapis.com/du-prd/books/images/9781250289568.jpg"
            ),
            Book(
                "9780385550369",
                15,
                "JAMES",
                "Percival Everett",
                "A reimagining of “Adventures of Huckleberry Finn” shines a different light on Mark Twain's classic, revealing new facets of the character of Jim.",
                "https://storage.googleapis.com/du-prd/books/images/9780385550369.jpg"
            )
        )
        val mockResponse = ApiResponse(ApiResults(mockBooks))

        `when`(apiService.getBooks(anyString())).thenReturn(Single.just(mockResponse))

        // Call the fetchBooks function
        val testObserver = bookRepository.fetchBooks()
            .test()
            .awaitDone(5, java.util.concurrent.TimeUnit.SECONDS)

        // Verify that the API was called
        verify(apiService).getBooks(anyString())

        // Assert that books were fetched and inserted into the database
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValue { books ->
            books.size == 2 &&
                    books[0].title == "PASSIONS IN DEATH" &&
                    books[1].title == "JAMES"
        }

        // Verify data in Room
        bookDao.getAllBooks()
            .test()
            .assertValue { books ->
                books.size == 2 && books[0].primary_isbn13 == "9781250289568"
            }
    }
}