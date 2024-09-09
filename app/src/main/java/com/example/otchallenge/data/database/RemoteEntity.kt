package com.example.otchallenge.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "book_remote_keys",
)
data class RemoteKey(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val nextPage: Int?,
    val lastUpdated: Long,
)
