package com.example.otchallenge.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKeys(keys: List<RemoteKey>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKey(key: RemoteKey)

    @Query(
        """
        SELECT * FROM book_remote_keys WHERE id=:key
    """,
    )
    suspend fun getKeyById(key: String): RemoteKey?

    @Query(
        """
        DELETE FROM book_remote_keys
    """,
    )
    suspend fun clearKeys()
}
