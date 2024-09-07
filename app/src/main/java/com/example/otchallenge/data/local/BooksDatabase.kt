package com.example.otchallenge.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.otchallenge.data.Book

@Database(entities = [Book::class], version = 1)
@TypeConverters(BuyLinkListTypeConverter::class)
abstract class BooksDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDao

    companion object {
        @Volatile
        private var INSTANCE: BooksDatabase? = null

        fun getDatabase(context: Context): BooksDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BooksDatabase::class.java,
                    "books_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}