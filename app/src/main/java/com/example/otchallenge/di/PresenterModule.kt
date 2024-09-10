package com.example.otchallenge.di

import com.example.otchallenge.model.BooksListPresenter
import com.example.otchallenge.model.BooksListPresenterImpl
import dagger.Binds
import dagger.Module

@Module
abstract class PresenterModule {

    @Binds
    abstract fun bindBookListPresenter(presenter: BooksListPresenterImpl): BooksListPresenter
}