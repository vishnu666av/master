package com.example.otchallenge.di

import com.example.otchallenge.data.BooksRepoImpl
import com.example.otchallenge.data.local.BookDao
import com.example.otchallenge.data.remote.BooksApi
import com.example.otchallenge.domain.BooksRepo
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DomainModule {

    @Binds
    fun bindsBooksRepo(booksRepoImpl: BooksRepoImpl): BooksRepo

    companion object {
        @Provides
        fun providesBooksRepoImpl(booksApi: BooksApi, bookDao: BookDao) =
            BooksRepoImpl(booksApi, bookDao)
    }
}