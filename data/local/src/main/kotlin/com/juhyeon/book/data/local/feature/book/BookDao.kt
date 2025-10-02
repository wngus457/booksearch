package com.juhyeon.book.data.local.feature.book

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookEntity: BookEntity)

    @Query("DELETE FROM book WHERE id = :id")
    suspend fun deleteBookmark(id: String)

    @Query("SELECT * FROM book WHERE (title LIKE :keyword OR authors LIKE :keyword)")
    fun getAllBookmarks(keyword: String): Flow<List<BookEntity>>

    @Query("SELECT id FROM book")
    fun getAllBookmarkKeys(): Flow<List<String>>

    @Query("SELECT * FROM book WHERE id = :id LIMIT 1")
    fun getBookmark(id: String): Flow<BookEntity?>
}