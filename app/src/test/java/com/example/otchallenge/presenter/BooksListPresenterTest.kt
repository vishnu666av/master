package com.example.otchallenge.presenter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.otchallenge.model.BookDto
import com.example.otchallenge.model.Repository
import com.example.otchallenge.ui.bookslist.presenter.BooksListPresenter
import com.example.otchallenge.ui.bookslist.presenter.BooksListUiState
import com.example.otchallenge.usecase.BooksListUseCase
import com.example.otchallenge.utils.testObserve
import com.example.otchallenge.utils.testPrototype
import com.example.otchallenge.utils.testPrototypeList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class BooksListPresenterTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var localRepository: Repository<BookDto>

    @Mock
    private lateinit var remoteRepository: Repository<BookDto>

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        MockitoAnnotations.openMocks(this)

        setUpRepositories()
    }

    private fun setUpRepositories() {
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
    fun when_remote_repository_has_data_then_presenter_online_list_state() =
        runTest(testDispatcher) {
            // Arrange
            `when`(localRepository.all()).thenThrow(IllegalStateException())
            `when`(remoteRepository.all()).thenReturn(BookDto.testPrototypeList())

            val useCase = BooksListUseCase(localRepository, remoteRepository)
            val presenter = BooksListPresenter(useCase)

            // Act
            presenter.getBooks()
            val uiState = presenter.uiState.testObserve()

            // Assert
            assert(uiState is BooksListUiState.OnlineList)
            assert((uiState as BooksListUiState.OnlineList).items.size == 1)
            assert(uiState.items[0] == BookDto.testPrototype())
        }

    @Test
    fun when_remote_repository_empty_and_local_repository_has_data_then_presenter_offline_list() =
        runTest(testDispatcher) {
            // Arrange
            `when`(localRepository.all()).thenReturn(BookDto.testPrototypeList())
            `when`(remoteRepository.all()).thenReturn(listOf())

            val useCase = BooksListUseCase(localRepository, remoteRepository)
            val presenter = BooksListPresenter(useCase)

            // Act
            presenter.getBooks()
            val uiState = presenter.uiState.testObserve()

            // Assert
            assert(uiState is BooksListUiState.OfflineList)
            uiState as BooksListUiState.OfflineList
            assert(uiState.items.size == 1)
            assert(uiState.items[0] == BookDto.testPrototype())
        }

    @Test
    fun when_remote_repository_error_and_local_repository_empty_then_presenter_empty() =
        runTest(testDispatcher) {
            // Arrange
            `when`(localRepository.all()).thenReturn(listOf())
            `when`(remoteRepository.all()).thenThrow(IllegalStateException())

            val useCase = BooksListUseCase(localRepository, remoteRepository)
            val presenter = BooksListPresenter(useCase)

            // Act
            presenter.getBooks()
            val uiState = presenter.uiState.testObserve()

            // Assert
            assert(uiState is BooksListUiState.Empty)
        }

    @Test
    fun when_remote_repository_empty_and_local_repository_cached_list_then_presenter_offline_list() =
        runTest(testDispatcher) {
            // Arrange
            `when`(localRepository.all()).thenReturn(BookDto.testPrototypeList())
            `when`(remoteRepository.all()).thenReturn(listOf())

            val useCase = BooksListUseCase(localRepository, remoteRepository)
            val presenter = BooksListPresenter(useCase)

            // Act
            presenter.getBooks()
            val uiState = presenter.uiState.testObserve()

            // Assert
            assert(uiState is BooksListUiState.OfflineList)
            uiState as BooksListUiState.OfflineList
            assert(uiState.items.size == 1)
            assert(uiState.items[0] == BookDto.testPrototype())
        }

    @Test
    fun when_remote_repository_error_and_local_repository_cached_list_then_presenter_offline_list() =
        runTest(testDispatcher) {
            // Arrange
            `when`(localRepository.all()).thenReturn(BookDto.testPrototypeList())
            `when`(remoteRepository.all()).thenThrow(IllegalStateException())

            val useCase = BooksListUseCase(localRepository, remoteRepository)
            val presenter = BooksListPresenter(useCase)

            // Act
            presenter.getBooks()
            val uiState = presenter.uiState.testObserve()

            // Assert
            assert(uiState is BooksListUiState.OfflineList)
            uiState as BooksListUiState.OfflineList
            assert(uiState.items.size == 1)
            assert(uiState.items[0] == BookDto.testPrototype())
        }
}