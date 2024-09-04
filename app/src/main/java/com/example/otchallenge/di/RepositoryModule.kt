package com.example.otchallenge.di

import com.example.otchallenge.data.remote.BookApi
import com.example.otchallenge.data.repository.BookRepositoryImpl
import com.example.otchallenge.common.util.ConnectivityProvider
import com.example.otchallenge.data.local.room.BookDao
import com.example.otchallenge.domain.repository.BookRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideBookRepository(newsApi: BookApi, dao: BookDao, connectivityProvider: ConnectivityProvider): BookRepository =
        BookRepositoryImpl(newsApi, dao, connectivityProvider)
}