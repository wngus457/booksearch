package com.juhyeon.book.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.juhyeon.book.data.local.feature.book.BookDao
import com.juhyeon.book.data.local.feature.book.BookEntity

@Database(
    entities = [
        BookEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDao

    companion object {
        private const val DATABASE_NAME = "book-room-db"
        fun buildDatabase(context: Context) = Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration(false)
            .build()
    }
}