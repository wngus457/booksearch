package com.juhyeon.book.data.local.di

import android.content.Context
import com.juhyeon.book.data.local.db.AppDatabase
import com.juhyeon.book.data.local.feature.book.BookDao
import com.juhyeon.book.data.local.feature.book.BookLocalDataSourceImpl
import com.juhyeon.book.data.repository.feature.book.BookLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalModule {

    @Singleton
    @Binds
    abstract fun bindBookLocalDataSource(source: BookLocalDataSourceImpl): BookLocalDataSource

    companion object {
        @Singleton
        @Provides
        fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase = AppDatabase.buildDatabase(context)

        @Singleton
        @Provides
        fun provideBookDao(appDatabase: AppDatabase): BookDao = appDatabase.bookDao()
    }
}