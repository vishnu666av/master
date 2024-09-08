package com.example.otchallenge.model

import com.example.otchallenge.data.BookDto
import com.example.otchallenge.data.Repository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class BooksListPresenterImplTest {

    @Mock
    private lateinit var localRepository: Repository<BookDto>

    @Mock
    private lateinit var remoteRepository: Repository<BookDto>

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        localRepository = Mockito.spy(object : Repository<BookDto> {
            private val localList = mutableListOf<BookDto>()

            override suspend fun all(): List<BookDto> = localList

            override suspend fun save(items: List<BookDto>) {
                localList.addAll(items)
            }
        })

        remoteRepository = Mockito.spy(object : Repository<BookDto> {
            private val remoteList = mutableListOf<BookDto>()

            override suspend fun all(): List<BookDto> = remoteList

            override suspend fun save(items: List<BookDto>) {
                remoteList.addAll(items)
            }
        })
    }

    @Test
    fun when_remote_repo_fresh_list_then_it_saves_list_in_local_repo() = runTest {
        // Arrange
        val presenter = BooksListPresenterImpl(
            localRepository = localRepository,
            remoteRepository = remoteRepository
        )

        remoteRepository.save(BookDto.testPrototypeList())

        // Act
        val result = presenter.getBooks()

        // Assert
        assert(remoteRepository.all().size == 1)
        assert(localRepository.all().size == 1)
        assert(localRepository.all()[0] == BookDto.testPrototype())
        assert(result is BooksListDataState.FreshList)
        assert((result as BooksListDataState.FreshList).items.size == 1)
        assert(result.items[0] == BookDto.testPrototype())
    }

    @Test
    fun when_remote_repo_empty_then_stale_list_from_local_repo() = runTest {
        // Arrange
        val presenter = BooksListPresenterImpl(
            localRepository = localRepository,
            remoteRepository = remoteRepository
        )

        localRepository.save(BookDto.testPrototypeList())

        // Act
        val result = presenter.getBooks()

        // Assert
        assert(remoteRepository.all().isEmpty())
        assert(result is BooksListDataState.StaleList)
        assert(localRepository.all().size == 1)
        assert(localRepository.all()[0] == BookDto.testPrototype())
    }

    @Test
    fun when_remote_repo_error_then_stale_list_from_local_repo() = runTest {
        // Arrange
        val presenter = BooksListPresenterImpl(
            localRepository = localRepository,
            remoteRepository = remoteRepository
        )

        localRepository.save(BookDto.testPrototypeList())

        `when`(remoteRepository.all()).thenThrow(IllegalStateException())

        // Act
        val result = presenter.getBooks()

        // Assert
        assert(result is BooksListDataState.StaleList)
        assert(localRepository.all().size == 1)
        assert(localRepository.all()[0] == BookDto.testPrototype())
    }

    @Test
    fun when_remote_repo_empty_and_local_repo_empty_then_empty() = runTest {
        // Arrange
        val presenter = BooksListPresenterImpl(
            localRepository = localRepository,
            remoteRepository = remoteRepository
        )

        // Act
        val result = presenter.getBooks()

        // Assert
        assert(localRepository.all().isEmpty())
        assert(remoteRepository.all().isEmpty())
        assert(result is BooksListDataState.Empty)
    }

    @Test
    fun when_remote_repo_empty_and_local_repo_error_then_error() = runTest {
        // Arrange
        val presenter = BooksListPresenterImpl(
            localRepository = localRepository,
            remoteRepository = remoteRepository
        )

        `when`(localRepository.all()).thenThrow(IllegalStateException())

        // Act
        val result = presenter.getBooks()

        // Assert
        assert(remoteRepository.all().isEmpty())
        assert(result is BooksListDataState.Error)
    }

    @Test
    fun when_remote_repo_empty_and_local_repo_stale_list_then_stale_list() = runTest {
        // Arrange
        val presenter = BooksListPresenterImpl(
            localRepository = localRepository,
            remoteRepository = remoteRepository
        )

        localRepository.save(BookDto.testPrototypeList())

        // Act
        val result = presenter.getBooks()

        // Assert
        assert(localRepository.all().size == 1)
        assert(localRepository.all()[0] == BookDto.testPrototype())
        assert(remoteRepository.all().isEmpty())
        assert(result is BooksListDataState.StaleList)
        assert((result as BooksListDataState.StaleList).items.size == 1)
        assert(result.items[0] == BookDto.testPrototype())
    }

    @Test
    fun when_remote_repo_error_and_local_repo_error_then_error() = runTest {
        // Arrange
        val presenter = BooksListPresenterImpl(
            localRepository = localRepository,
            remoteRepository = remoteRepository
        )

        `when`(localRepository.all()).thenThrow(IllegalStateException())
        `when`(remoteRepository.all()).thenThrow(IllegalStateException())

        // Act
        val result = presenter.getBooks()

        // Assert
        assert(result is BooksListDataState.Error)
    }

    private fun BookDto.Companion.testPrototype() = BookDto(
        rank = 1,
        title = "book title",
        author = "book author",
        description = "book description",
        imageUrl = "book imageUrk",
        buyLinks = listOf()
    )

    private fun BookDto.Companion.testPrototypeList() = listOf(testPrototype())
}