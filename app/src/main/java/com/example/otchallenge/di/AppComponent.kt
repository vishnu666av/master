package com.example.otchallenge.di

import com.example.otchallenge.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ViewModelModule::class,
        RepositoryModule::class,
        RepositoryImplementationsModule::class
    ]
)
interface AppComponent {
    fun inject(activity: MainActivity)
}
