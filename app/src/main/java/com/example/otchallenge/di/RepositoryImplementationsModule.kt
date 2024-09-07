package com.example.otchallenge.di

import com.example.otchallenge.data.LocalFictionsRepository
import com.example.otchallenge.data.RemoteFictionsRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryImplementationsModule {

    @Provides
    fun provideLocalRepository() = LocalFictionsRepository()

    @Provides
    fun provideRemoteRepository() = RemoteFictionsRepository()
}