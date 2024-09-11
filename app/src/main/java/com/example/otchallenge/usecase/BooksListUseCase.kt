package com.example.otchallenge.usecase

import com.example.otchallenge.di.LocalRepository
import com.example.otchallenge.di.RemoteRepository
import com.example.otchallenge.model.BookDto
import com.example.otchallenge.model.Repository
import javax.inject.Inject

class BooksListUseCase @Inject constructor(
    @LocalRepository private val localRepository: Repository<BookDto>,
    @RemoteRepository private val remoteRepository: Repository<BookDto>
) : IBooksListUseCase {

    override suspend fun getBooks(): BooksListUseCaseResult = when (val result = getRemoteData()) {
        /**
         * [future improvement]
         * 1. This is the place to save data locally or check for an existing cache
         * before making a call.
         *
         * 2. In case of network call failure we can return a new state that carries a list
         * of cached items and message about the error.
         */
        is BooksListUseCaseResult.FreshList -> result
        else -> getLocalData()
    }

    private suspend fun getRemoteData(): BooksListUseCaseResult = try {
        val items = remoteRepository.all()
        if (items.isEmpty()) {
            BooksListUseCaseResult.Empty
        } else {
            BooksListUseCaseResult.FreshList(items = items)
        }
    } catch (e: Exception) {
        BooksListUseCaseResult.Error
    }

    private suspend fun getLocalData(): BooksListUseCaseResult = try {
        val items = localRepository.all()
        if (items.isEmpty()) {
            BooksListUseCaseResult.Empty
        } else {
            BooksListUseCaseResult.CachedList(items = items)
        }
    } catch (e: Exception) {
        BooksListUseCaseResult.Error
    }
}