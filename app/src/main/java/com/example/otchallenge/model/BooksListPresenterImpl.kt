package com.example.otchallenge.model

import com.example.otchallenge.data.BookDto
import com.example.otchallenge.data.Repository
import com.example.otchallenge.di.LocalRepository
import com.example.otchallenge.di.RemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * the presenter is where we embed our business logic.
 * in this case, the logic is to try displaying fresh online data and fall back
 * on local database if the call fails or internet connection is not available.
 */
class BooksListPresenterImpl @Inject constructor(
    @LocalRepository private val localRepository: Repository<BookDto>,
    @RemoteRepository private val remoteRepository: Repository<BookDto>
) : BooksListPresenter {

    override suspend fun getBooks(): BooksListDataState = withContext(Dispatchers.IO) {
        when (val remoteResult = getRemoteData()) {
            is BooksListDataState.FreshList -> {
                localRepository.save(remoteResult.items)
                remoteResult
            }

            else -> getLocalData()
        }
    }

    private suspend fun getRemoteData(): BooksListDataState = try {
        val items = remoteRepository.all()
        if (items.isEmpty()) {
            BooksListDataState.Empty(timestamp = "")
        } else {
            BooksListDataState.FreshList(items = items, timestamp = "")
        }
    } catch (e: Exception) {
        BooksListDataState.Error(message = e.localizedMessage, timestamp = "")
    }

    private suspend fun getLocalData(): BooksListDataState = try {
        val items = localRepository.all()
        if (items.isEmpty()) {
            BooksListDataState.Empty(timestamp = "")
        } else {
            BooksListDataState.StaleList(items = items, timestamp = "")
        }
    } catch (e: Exception) {
        BooksListDataState.Error(message = e.localizedMessage, timestamp = "")
    }
}