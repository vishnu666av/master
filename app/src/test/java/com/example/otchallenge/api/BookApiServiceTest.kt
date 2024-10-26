package com.example.otchallenge.api

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.otchallenge.api.BookApiService
import com.example.otchallenge.model.BookResponse
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class BookApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var bookApiService: BookApiService
    private val jsonResponse = "{\"status\":\"OK\",\"copyright\":\"Copyright (c) 2024 The New York Times Company.  All Rights Reserved.\",\"num_results\":15,\"last_modified\":\"2024-10-16T22:04:46-04:00\",\"results\":{\"list_name\":\"Hardcover Fiction\",\"list_name_encoded\":\"hardcover-fiction\",\"bestsellers_date\":\"2024-10-12\",\"published_date\":\"2024-10-27\",\"published_date_description\":\"latest\",\"next_published_date\":\"\",\"previous_published_date\":\"2024-10-20\",\"display_name\":\"Hardcover Fiction\",\"normal_list_ends_at\":15,\"updated\":\"WEEKLY\",\"books\":[{\"rank\":1,\"rank_last_week\":1,\"weeks_on_list\":3,\"asterisk\":0,\"dagger\":0,\"primary_isbn10\":\"0593449592\",\"primary_isbn13\":\"9780593449592\",\"publisher\":\"Random House\",\"description\":\"A man in search of the father he never knew encounters a single mom and rumors circulate of the nearby appearance of a white deer.\",\"price\":\"0.00\",\"title\":\"COUNTING MIRACLES\",\"author\":\"Nicholas Sparks\",\"contributor\":\"by Nicholas Sparks\",\"contributor_note\":\"\",\"book_image\":\"https:\\/\\/storage.googleapis.com\\/du-prd\\/books\\/images\\/9780593449592.jpg\",\"book_image_width\":330,\"book_image_height\":500,\"amazon_product_url\":\"https:\\/\\/www.amazon.com\\/dp\\/0593449592?tag=thenewyorktim-20\",\"age_group\":\"\",\"book_review_link\":\"\",\"first_chapter_link\":\"\",\"sunday_review_link\":\"\",\"article_chapter_link\":\"\",\"isbns\":[{\"isbn10\":\"0593449592\",\"isbn13\":\"9780593449592\"},{\"isbn10\":\"0593449606\",\"isbn13\":\"9780593449608\"}],\"buy_links\":[{\"name\":\"Amazon\",\"url\":\"https:\\/\\/www.amazon.com\\/dp\\/0593449592?tag=thenewyorktim-20\"},{\"name\":\"Apple Books\",\"url\":\"https:\\/\\/goto.applebooks.apple\\/9780593449592?at=10lIEQ\"},{\"name\":\"Barnes and Noble\",\"url\":\"https:\\/\\/www.anrdoezrs.net\\/click-7990613-11819508?url=https%3A%2F%2Fwww.barnesandnoble.com%2Fw%2F%3Fean%3D9780593449592\"},{\"name\":\"Books-A-Million\",\"url\":\"https:\\/\\/www.anrdoezrs.net\\/click-7990613-35140?url=https%3A%2F%2Fwww.booksamillion.com%2Fp%2FCOUNTING%2BMIRACLES%2FNicholas%2BSparks%2F9780593449592\"},{\"name\":\"Bookshop.org\",\"url\":\"https:\\/\\/bookshop.org\\/a\\/3546\\/9780593449592\"}],\"book_uri\":\"nyt:\\/\\/book\\/974caeee-6bf0-524c-809c-29bfdd99d65f\"},{\"rank\":2,\"rank_last_week\":0,\"weeks_on_list\":1,\"asterisk\":0,\"dagger\":0,\"primary_isbn10\":\"1250355664\",\"primary_isbn13\":\"9781250355669\",\"publisher\":\"Bramble\",\"description\":\"Astraea must decide whether to sneak in as a substitute in the trials that will determine the safety of her kingdom.\",\"price\":\"0.00\",\"title\":\"THE STARS ARE DYING\",\"author\":\"Chloe C. Pe\\u00f1aranda\",\"contributor\":\"by Chloe C. Pe\\u00f1aranda\",\"contributor_note\":\"\",\"book_image\":\"https:\\/\\/storage.googleapis.com\\/du-prd\\/books\\/images\\/9781250355669.jpg\",\"book_image_width\":341,\"book_image_height\":500,\"amazon_product_url\":\"https:\\/\\/www.amazon.com\\/dp\\/1250355664?tag=thenewyorktim-20\",\"age_group\":\"\",\"book_review_link\":\"\",\"first_chapter_link\":\"\",\"sunday_review_link\":\"\",\"article_chapter_link\":\"\",\"isbns\":[{\"isbn10\":\"1250355664\",\"isbn13\":\"9781250355669\"},{\"isbn10\":\"1250370442\",\"isbn13\":\"9781250370440\"}],\"buy_links\":[{\"name\":\"Amazon\",\"url\":\"https:\\/\\/www.amazon.com\\/dp\\/1250355664?tag=thenewyorktim-20\"},{\"name\":\"Apple Books\",\"url\":\"https:\\/\\/goto.applebooks.apple\\/9781250355669?at=10lIEQ\"},{\"name\":\"Barnes and Noble\",\"url\":\"https:\\/\\/www.anrdoezrs.net\\/click-7990613-11819508?url=https%3A%2F%2Fwww.barnesandnoble.com%2Fw%2F%3Fean%3D9781250355669\"},{\"name\":\"Books-A-Million\",\"url\":\"https:\\/\\/www.anrdoezrs.net\\/click-7990613-35140?url=https%3A%2F%2Fwww.booksamillion.com%2Fp%2FTHE%2BSTARS%2BARE%2BDYING%2FChloe%2BC.%2BPe%25C3%25B1aranda%2F9781250355669\"},{\"name\":\"Bookshop.org\",\"url\":\"https:\\/\\/bookshop.org\\/a\\/3546\\/9781250355669\"}],\"book_uri\":\"nyt:\\/\\/book\\/00d75ee8-72b6-5ca0-a903-a770b5b6901c\"}," +
            "{\"rank\":14,\"rank_last_week\":14,\"weeks_on_list\":5,\"asterisk\":0,\"dagger\":0,\"primary_isbn10\":\"0593446097\",\"primary_isbn13\":\"9780593446096\",\"publisher\":\"Random House\",\"description\":\"As a murder casts a pall on a town in Maine, Lucy Barton, Olive Kitteridge and Bob Burgess share stories and seek meaning.\",\"price\":\"0.00\",\"title\":\"TELL ME EVERYTHING\",\"author\":\"Elizabeth Strout\",\"contributor\":\"by Elizabeth Strout\",\"contributor_note\":\"\",\"book_image\":\"https:\\/\\/storage.googleapis.com\\/du-prd\\/books\\/images\\/9780593446096.jpg\",\"book_image_width\":331,\"book_image_height\":500,\"amazon_product_url\":\"https:\\/\\/www.amazon.com\\/dp\\/0593446097?tag=thenewyorktim-20\",\"age_group\":\"\",\"book_review_link\":\"\",\"first_chapter_link\":\"\",\"sunday_review_link\":\"\",\"article_chapter_link\":\"\",\"isbns\":[{\"isbn10\":\"0593446097\",\"isbn13\":\"9780593446096\"},{\"isbn10\":\"0593446100\",\"isbn13\":\"9780593446102\"}],\"buy_links\":[{\"name\":\"Amazon\",\"url\":\"https:\\/\\/www.amazon.com\\/dp\\/0593446097?tag=thenewyorktim-20\"},{\"name\":\"Apple Books\",\"url\":\"https:\\/\\/goto.applebooks.apple\\/9780593446096?at=10lIEQ\"},{\"name\":\"Barnes and Noble\",\"url\":\"https:\\/\\/www.anrdoezrs.net\\/click-7990613-11819508?url=https%3A%2F%2Fwww.barnesandnoble.com%2Fw%2F%3Fean%3D9780593446096\"},{\"name\":\"Books-A-Million\",\"url\":\"https:\\/\\/www.anrdoezrs.net\\/click-7990613-35140?url=https%3A%2F%2Fwww.booksamillion.com%2Fp%2FTELL%2BME%2BEVERYTHING%2FElizabeth%2BStrout%2F9780593446096\"},{\"name\":\"Bookshop.org\",\"url\":\"https:\\/\\/bookshop.org\\/a\\/3546\\/9780593446096\"}],\"book_uri\":\"nyt:\\/\\/book\\/3f5b9449-8053-58fe-b24d-67c77f14d384\"},{\"rank\":15,\"rank_last_week\":0,\"weeks_on_list\":14,\"asterisk\":0,\"dagger\":0,\"primary_isbn10\":\"1668037718\",\"primary_isbn13\":\"9781668037713\",\"publisher\":\"Scribner\",\"description\":\"A dozen short stories that explore darkness in literal and metaphorical forms.\",\"price\":\"0.00\",\"title\":\"YOU LIKE IT DARKER\",\"author\":\"Stephen King\",\"contributor\":\"by Stephen King\",\"contributor_note\":\"\",\"book_image\":\"https:\\/\\/storage.googleapis.com\\/du-prd\\/books\\/images\\/9781668037713.jpg\",\"book_image_width\":329,\"book_image_height\":500,\"amazon_product_url\":\"https:\\/\\/www.amazon.com\\/dp\\/1668037718?tag=thenewyorktim-20\",\"age_group\":\"\",\"book_review_link\":\"\",\"first_chapter_link\":\"\",\"sunday_review_link\":\"\",\"article_chapter_link\":\"\",\"isbns\":[{\"isbn10\":\"1668037718\",\"isbn13\":\"9781668037713\"},{\"isbn10\":\"1668037734\",\"isbn13\":\"9781668037737\"},{\"isbn10\":\"1797174622\",\"isbn13\":\"9781797174624\"}],\"buy_links\":[{\"name\":\"Amazon\",\"url\":\"https:\\/\\/www.amazon.com\\/dp\\/1668037718?tag=thenewyorktim-20\"},{\"name\":\"Apple Books\",\"url\":\"https:\\/\\/goto.applebooks.apple\\/9781668037713?at=10lIEQ\"},{\"name\":\"Barnes and Noble\",\"url\":\"https:\\/\\/www.anrdoezrs.net\\/click-7990613-11819508?url=https%3A%2F%2Fwww.barnesandnoble.com%2Fw%2F%3Fean%3D9781668037713\"},{\"name\":\"Books-A-Million\",\"url\":\"https:\\/\\/www.anrdoezrs.net\\/click-7990613-35140?url=https%3A%2F%2Fwww.booksamillion.com%2Fp%2FYOU%2BLIKE%2BIT%2BDARKER%2FStephen%2BKing%2F9781668037713\"},{\"name\":\"Bookshop.org\",\"url\":\"https:\\/\\/bookshop.org\\/a\\/3546\\/9781668037713\"}],\"book_uri\":\"nyt:\\/\\/book\\/84fc5c9e-1607-56f8-afcd-0ee37bfc8237\"}],\"corrections\":[]}}"
    @Before
    fun setUp() {
       mockWebServer = MockWebServer()
       mockWebServer.start()
       bookApiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(BookApiService::class.java)
    }

    @After
    fun tearDown(){
        mockWebServer.shutdown()
    }

    @Test
    fun `getBooks should return expected data`(){
        val apiKey = "KoRB4K5LRHygfjCL2AH6iQ7NeUqDAGAB"
        val mockResponse = MockResponse()
            .setBody(jsonResponse)
            .setResponseCode(200)
        mockWebServer.enqueue(mockResponse)
        val testObserver = TestObserver<BookResponse>()
        bookApiService.getBooks(apiKey, 0).subscribe(testObserver)

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValue { response ->
            response.status == "OK" && response.numResults == 15
        }
    }

    @Test
    fun `getBooks should handle no internet`(){
        val context = Mockito.mock(Context::class.java)
        val connectivityManager = Mockito.mock(ConnectivityManager::class.java)
        val networkCapabilities = Mockito.mock(NetworkCapabilities::class.java)

        Mockito.`when`(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(connectivityManager)
        Mockito.`when`(connectivityManager.activeNetwork).thenReturn(null)
        Mockito.`when`(connectivityManager.getNetworkCapabilities(null)).thenReturn(networkCapabilities)
        Mockito.`when`(networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)).thenReturn(false)

        val testObserver = TestObserver<BookResponse>()

        Single.error<BookResponse>(Throwable("No internet connection"))
            .subscribe(testObserver)
        testObserver.assertNotComplete()
        testObserver.assertError { error ->
            error.message == "No internet connection"
        }

    }

}