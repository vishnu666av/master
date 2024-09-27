package com.example.otchallenge

import com.example.otchallenge.model.Book
import com.example.otchallenge.presenter.BooksContract
import com.example.otchallenge.presenter.BooksPresenter
import com.example.otchallenge.repository.BookRepository
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class BooksPresenterTest {
    // Mock repository and view
    private lateinit var repository: BookRepository
    private lateinit var view: BooksContract.View
    private lateinit var presenter: BooksPresenter

    @Before
    fun setUp() {
        repository = mock(BookRepository::class.java)
        view = mock(BooksContract.View::class.java)

        presenter = BooksPresenter(repository)

        // Ensure that all RxJava operations run on a single thread in tests
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }

        // Replace AndroidSchedulers.mainThread() with trampoline scheduler in tests
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun fetchBooks_success() {
        // Create a fake book list
        val books = listOf(
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
                "",
                //"JAMES",
                "Percival Everett",
                "A reimagining of “Adventures of Huckleberry Finn” shines a different light on Mark Twain's classic, revealing new facets of the character of Jim.",
                "https://storage.googleapis.com/du-prd/books/images/9780385550369.jpg"
            )
        )

        // Mock repository to return the book list by Single
        `when`(repository.fetchBooks()).thenReturn(Single.just(books))

        // Attach the view to the presenter
        presenter.attach(view)

        // Call fetchBooks method
        presenter.fetchBooks()

        // Verify that the view shows the progress bar
        verify(view).showProgress()

        // Verify that the view shows the book list
        verify(view).displayBooks(books)

        // Verify that the progress bar is hidden after fetching the books
        verify(view).hideProgress()

        // Verify that no errors were shown
        verify(view, never()).showError(anyString())

    }

    @Test
    fun fetchBooks_fail() {
        // Simulate a failure by returning an empty list or throwing an exception
        `when`(repository.fetchBooks()).thenReturn(Single.error(RuntimeException("Data load failed")))

        // Attach the view to the presenter
        presenter.attach(view)

        // Call fetchBooks method
        presenter.fetchBooks()

        // Verify that the view shows the progress bar first
        verify(view).showProgress()

        // Verify that an error message is shown when the fetching fails
        verify(view).showError("Data load failed")

        // Verify that the progress bar is hidden after the failure
        verify(view).hideProgress()

        // Verify that no books are displayed
        verify(view, never()).displayBooks(anyList())
    }

    @After
    fun tearDown() {
        // Reset plugins after each test to avoid any impact on other tests
        RxAndroidPlugins.reset()
        RxJavaPlugins.reset()
    }
}