package com.example.otchallenge.di

import android.content.Context
import androidx.room.Room
import com.example.otchallenge.data.database.AppDatabase
import com.example.otchallenge.data.database.BookDao
import com.example.otchallenge.data.database.RemoteKeyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    fun provideBookDao(appDb: AppDatabase): BookDao = appDb.bookDao()

    @Provides
    fun provideRemoteKeyDao(appDb: AppDatabase): RemoteKeyDao = appDb.remoteKeyDao()

    @Provides
    fun provideDatabase(
        @ApplicationContext applicationContext: Context,
    ): AppDatabase =
        Room
            .databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                "otc-books-db",
            ).build()
}
