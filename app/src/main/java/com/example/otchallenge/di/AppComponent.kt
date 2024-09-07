package com.example.otchallenge.di

import com.example.otchallenge.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ViewModelModule::class,
        NetworkModule::class,
        DatabaseModule::class,
        RepositoryModule::class,
        RepositoryBindingsModule::class
    ]
)
interface AppComponent {
    fun inject(activity: MainActivity)
}
