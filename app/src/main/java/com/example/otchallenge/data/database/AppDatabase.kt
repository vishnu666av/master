package com.example.otchallenge.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BookEntity::class, RemoteKey::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao

    abstract fun remoteKeyDao(): RemoteKeyDao
}
