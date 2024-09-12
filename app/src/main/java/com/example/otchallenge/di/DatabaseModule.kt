package com.example.otchallenge.di

import android.content.Context
import androidx.room.Room
import com.example.otchallenge.data.local.room.BookDao
import com.example.otchallenge.data.local.room.BookDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): BookDatabase {
        return Room.databaseBuilder(
            context,
            BookDatabase::class.java,
            "book_database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideDataDao(database: BookDatabase): BookDao {
        return database.bookDao()
    }
}


