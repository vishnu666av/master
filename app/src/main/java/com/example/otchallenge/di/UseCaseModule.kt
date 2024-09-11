package com.example.otchallenge.di

import com.example.otchallenge.model.BookDto
import com.example.otchallenge.model.Repository
import com.example.otchallenge.usecase.BooksListUseCase
import com.example.otchallenge.usecase.IBooksListUseCase
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

    @Provides
    fun provideBooksListUseCase(
        @LocalRepository localRepository: Repository<BookDto>,
        @RemoteRepository remoteRepository: Repository<BookDto>
    ): IBooksListUseCase =
        BooksListUseCase(
            localRepository = localRepository,
            remoteRepository = remoteRepository
        )
}