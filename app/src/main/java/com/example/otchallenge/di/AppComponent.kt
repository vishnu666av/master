package com.example.otchallenge.di

import com.example.otchallenge.MainActivity
import dagger.Component
import javax.inject.Singleton
@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, DatabaseModule::class, RepositoryModule::class])
interface AppComponent {

    // Define injection targets
    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(appModule: AppModule): AppComponent
    }
}