package com.juhyeon.book.data.remote.di

import com.juhyeon.book.data.remote.feature.book.BookRemoteDataSourceImpl
import com.juhyeon.book.data.repository.feature.book.BookRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataModule {

    @Singleton
    @Binds
    abstract fun bindBookRemoteDataSource(source: BookRemoteDataSourceImpl): BookRemoteDataSource
}