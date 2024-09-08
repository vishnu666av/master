package com.example.otchallenge.di

import com.example.otchallenge.data.BookDto
import com.example.otchallenge.data.LocalBooksRepository
import com.example.otchallenge.data.RemoteBooksRepository
import com.example.otchallenge.data.Repository
import dagger.Binds
import dagger.Module
import javax.inject.Qualifier

@Module
abstract class RepositoryBindingsModule {

    @Binds
    @LocalRepository
    abstract fun bindLocalRepository(repository: LocalBooksRepository): Repository<BookDto>

    @Binds
    @RemoteRepository
    abstract fun bindRemoteRepository(repository: RemoteBooksRepository): Repository<BookDto>
}

/**
 * denotes a locally stored implementation of [Repository], e.g. a sqlite database
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LocalRepository

/**
 * denotes a remotely stored implementation of [Repository], e.g. a web api
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RemoteRepository