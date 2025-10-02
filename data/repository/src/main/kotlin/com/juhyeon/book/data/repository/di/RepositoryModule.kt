package com.juhyeon.book.data.repository.di

import com.juhyeon.book.data.repository.feature.book.BookRepositoryImpl
import com.juhyeon.book.domain.feature.book.BookRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindBookRepository(repository: BookRepositoryImpl): BookRepository
}