package com.example.otchallenge

import com.example.otchallenge.api.NYTApiService
import com.example.otchallenge.serialization.LocalDateTimeDeserializer
import com.google.gson.GsonBuilder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class NYTApiTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: NYTApiService

    @Before
    fun setup() {
        mockWebServer = MockWebServer()

        // ** Configure Retrofit to use the MockWebServer **
        val gson = GsonBuilder().registerTypeAdapter(
                LocalDateTime::class.java,
                LocalDateTimeDeserializer()
            ).create()

        apiService = Retrofit.Builder().baseUrl(mockWebServer.url("/"))
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
            .create(NYTApiService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `fetch all books successfully`() = runTest {
        val mockResponse = MockResponse()
            .setBody(DUMMY_RESPONSE)
            .setResponseCode(200)

        mockWebServer.enqueue(mockResponse)

        val responseDto = apiService.fetchBooks()

        assert(responseDto.copyright == "Copyright (c) 2024 The New York Times Company.  All Rights Reserved.")
        assert(responseDto.lastModified.format(DateTimeFormatter.ISO_DATE_TIME) == "2024-10-16T22:04:46") // <- ** Because we are using local date time, the offset would be zero!
        assert(responseDto.totalBooks == 15)
        assert(responseDto.result.listName == "Hardcover Fiction")
        assert(responseDto.result.books.size == 2)

        val firstBook = responseDto.result.books[0]
        assert(firstBook.rank == 1)
        assert(firstBook.description == "A man in search of the father he never knew encounters a single mom and rumors circulate of the nearby appearance of a white deer.")
        assert(firstBook.title == "COUNTING MIRACLES")
        assert(firstBook.author == "Nicholas Sparks")
        assert(firstBook.contributor == "by Nicholas Sparks")
        assert(firstBook.bookImage == "https://storage.googleapis.com/du-prd/books/images/9780593449592.jpg")
        assert(firstBook.isbn == "9780593449592")
        assert(firstBook.publisher == "Random House")

        val secondBook = responseDto.result.books[1]
        assert(secondBook.rank == 2)
        assert(secondBook.description == "Astraea must decide whether to sneak in as a substitute in the trials that will determine the safety of her kingdom.")
        assert(secondBook.title == "THE STARS ARE DYING")
        assert(secondBook.author == "Chloe C. Peñaranda")
        assert(secondBook.contributor == "by Chloe C. Peñaranda")
        assert(secondBook.bookImage == "https://storage.googleapis.com/du-prd/books/images/9781250355669.jpg")
        assert(secondBook.isbn == "9781250355669")
        assert(secondBook.publisher == "Bramble")

    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test(expected = HttpException::class) //< ** We are expecting an exception
    fun `fetch all books unsuccessfully`() = runTest {
        val mockResponse = MockResponse()
            .setResponseCode(500)

        mockWebServer.enqueue(mockResponse)


        apiService.fetchBooks()
    }

    companion object {
        val DUMMY_RESPONSE = """
{
    "status": "OK",
    "copyright": "Copyright (c) 2024 The New York Times Company.  All Rights Reserved.",
    "num_results": 15,
    "last_modified": "2024-10-16T22:04:46-04:00",
    "results": {
        "list_name": "Hardcover Fiction",
        "list_name_encoded": "hardcover-fiction",
        "bestsellers_date": "2024-10-12",
        "published_date": "2024-10-27",
        "published_date_description": "latest",
        "next_published_date": "",
        "previous_published_date": "2024-10-20",
        "display_name": "Hardcover Fiction",
        "normal_list_ends_at": 15,
        "updated": "WEEKLY",
        "books": [
            {
                "rank": 1,
                "rank_last_week": 1,
                "weeks_on_list": 3,
                "asterisk": 0,
                "dagger": 0,
                "primary_isbn10": "0593449592",
                "primary_isbn13": "9780593449592",
                "publisher": "Random House",
                "description": "A man in search of the father he never knew encounters a single mom and rumors circulate of the nearby appearance of a white deer.",
                "price": "0.00",
                "title": "COUNTING MIRACLES",
                "author": "Nicholas Sparks",
                "contributor": "by Nicholas Sparks",
                "contributor_note": "",
                "book_image": "https://storage.googleapis.com/du-prd/books/images/9780593449592.jpg",
                "book_image_width": 330,
                "book_image_height": 500,
                "amazon_product_url": "https://www.amazon.com/dp/0593449592?tag=thenewyorktim-20",
                "age_group": "",
                "book_review_link": "",
                "first_chapter_link": "",
                "sunday_review_link": "",
                "article_chapter_link": "",
                "isbns": [
                    {
                        "isbn10": "0593449592",
                        "isbn13": "9780593449592"
                    },
                    {
                        "isbn10": "0593449606",
                        "isbn13": "9780593449608"
                    }
                ],
                "buy_links": [
                    {
                        "name": "Amazon",
                        "url": "https://www.amazon.com/dp/0593449592?tag=thenewyorktim-20"
                    },
                    {
                        "name": "Apple Books",
                        "url": "https://goto.applebooks.apple/9780593449592?at=10lIEQ"
                    },
                    {
                        "name": "Barnes and Noble",
                        "url": "https://www.anrdoezrs.net/click-7990613-11819508?url=https%3A%2F%2Fwww.barnesandnoble.com%2Fw%2F%3Fean%3D9780593449592"
                    },
                    {
                        "name": "Books-A-Million",
                        "url": "https://www.anrdoezrs.net/click-7990613-35140?url=https%3A%2F%2Fwww.booksamillion.com%2Fp%2FCOUNTING%2BMIRACLES%2FNicholas%2BSparks%2F9780593449592"
                    },
                    {
                        "name": "Bookshop.org",
                        "url": "https://bookshop.org/a/3546/9780593449592"
                    }
                ],
                "book_uri": "nyt://book/974caeee-6bf0-524c-809c-29bfdd99d65f"
            },
            {
                "rank": 2,
                "rank_last_week": 0,
                "weeks_on_list": 1,
                "asterisk": 0,
                "dagger": 0,
                "primary_isbn10": "1250355664",
                "primary_isbn13": "9781250355669",
                "publisher": "Bramble",
                "description": "Astraea must decide whether to sneak in as a substitute in the trials that will determine the safety of her kingdom.",
                "price": "0.00",
                "title": "THE STARS ARE DYING",
                "author": "Chloe C. Peñaranda",
                "contributor": "by Chloe C. Peñaranda",
                "contributor_note": "",
                "book_image": "https://storage.googleapis.com/du-prd/books/images/9781250355669.jpg",
                "book_image_width": 341,
                "book_image_height": 500,
                "amazon_product_url": "https://www.amazon.com/dp/1250355664?tag=thenewyorktim-20",
                "age_group": "",
                "book_review_link": "",
                "first_chapter_link": "",
                "sunday_review_link": "",
                "article_chapter_link": "",
                "isbns": [
                    {
                        "isbn10": "1250355664",
                        "isbn13": "9781250355669"
                    },
                    {
                        "isbn10": "1250370442",
                        "isbn13": "9781250370440"
                    }
                ],
                "buy_links": [
                    {
                        "name": "Amazon",
                        "url": "https://www.amazon.com/dp/1250355664?tag=thenewyorktim-20"
                    },
                    {
                        "name": "Apple Books",
                        "url": "https://goto.applebooks.apple/9781250355669?at=10lIEQ"
                    },
                    {
                        "name": "Barnes and Noble",
                        "url": "https://www.anrdoezrs.net/click-7990613-11819508?url=https%3A%2F%2Fwww.barnesandnoble.com%2Fw%2F%3Fean%3D9781250355669"
                    },
                    {
                        "name": "Books-A-Million",
                        "url": "https://www.anrdoezrs.net/click-7990613-35140?url=https%3A%2F%2Fwww.booksamillion.com%2Fp%2FTHE%2BSTARS%2BARE%2BDYING%2FChloe%2BC.%2BPe%25C3%25B1aranda%2F9781250355669"
                    },
                    {
                        "name": "Bookshop.org",
                        "url": "https://bookshop.org/a/3546/9781250355669"
                    }
                ],
                "book_uri": "nyt://book/00d75ee8-72b6-5ca0-a903-a770b5b6901c"
            }
        ],
        "corrections": []
    }
}
            """.trimIndent()
    }
}