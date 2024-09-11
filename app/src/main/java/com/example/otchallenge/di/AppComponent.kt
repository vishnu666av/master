package com.example.otchallenge.di

import com.example.otchallenge.ui.bookslist.view.BooksListActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        DatabaseModule::class,
        RepositoryModule::class,
        PresenterModule::class,
        UseCaseModule::class,
        CoroutineModule::class
    ]
)
interface AppComponent {
    fun inject(activity: BooksListActivity)
}
