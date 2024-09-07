package com.example.otchallenge.di

import com.example.otchallenge.data.LocalFictionsRepository
import com.example.otchallenge.data.RemoteFictionsRepository
import com.example.otchallenge.data.Repository
import com.example.otchallenge.model.Fiction
import dagger.Binds
import dagger.Module
import javax.inject.Qualifier

@Module
abstract class RepositoryModule {

    @Binds
    @LocalRepository
    abstract fun bindLocalRepository(repository: LocalFictionsRepository): Repository<Fiction>

    @Binds
    @RemoteRepository
    abstract fun bindRemoteRepository(repository: RemoteFictionsRepository): Repository<Fiction>
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