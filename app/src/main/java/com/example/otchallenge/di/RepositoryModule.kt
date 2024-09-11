package com.example.otchallenge.di

import com.example.otchallenge.model.BookDto
import com.example.otchallenge.model.Repository
import com.example.otchallenge.model.local.LocalBooksRepository
import com.example.otchallenge.model.network.RemoteBooksRepository
import dagger.Binds
import dagger.Module
import javax.inject.Qualifier

@Module
abstract class RepositoryModule {

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