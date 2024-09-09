package com.example.otchallenge.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllBooks(books: List<BookEntity>)

    @Query(
        """
        SELECT * FROM books order by rank ASC
    """,
    )
    fun fetchPagingData(): PagingSource<Int, BookEntity>
    @Query(
        """
        SELECT COUNT(rank) FROM books 
    """,
    )
    fun getCount(): Int

    @Query("""DELETE FROM books""")
    suspend fun nukeTable()
}
