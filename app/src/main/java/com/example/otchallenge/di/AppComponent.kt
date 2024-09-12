package com.example.otchallenge.di

import com.example.otchallenge.MainActivity
import com.example.otchallenge.presentation.bookList.BookFragment
import dagger.Component
import javax.inject.Singleton
@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, DatabaseModule::class, RepositoryModule::class, PresenterModule::class])
interface AppComponent {

    // Define injection targets
    fun inject(activity: MainActivity)

    fun inject(fragment: BookFragment)

    @Component.Factory
    interface Factory {
        fun create(appModule: AppModule): AppComponent
    }
}