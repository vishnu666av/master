package com.example.otchallenge.di

import com.example.otchallenge.ui.bookslist.presenter.BooksListPresenter
import com.example.otchallenge.ui.bookslist.presenter.IBooksListPresenter
import com.example.otchallenge.usecase.IBooksListUseCase
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
class PresenterModule {

    @Provides
    @Singleton
    fun provideBookListPresenter(
        useCase: IBooksListUseCase,
        @MainDispatcher mainDispatcher: CoroutineDispatcher,
        @IODispatcher ioDispatcher: CoroutineDispatcher
    ): IBooksListPresenter =
        BooksListPresenter(
            useCase = useCase,
            mainDispatcher = mainDispatcher,
            ioDispatcher = ioDispatcher
        )
}