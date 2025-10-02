package com.juhyeon.book.data.remote.di

import com.juhyeon.book.data.remote.BuildConfig
import com.juhyeon.book.data.remote.KAKAO_BOOK_HOST
import com.juhyeon.book.data.remote.service.BookService
import com.juhyeon.book.shared.util.common.LogHelper
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(logHelper: LogHelper): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)
            )
            .addInterceptor { chain ->
                val request = chain.request().newBuilder().addHeader("Authorization", "KakaoAK ${BuildConfig.KAKAO_BOOK_KEY}")
                val response = chain.proceed(request.build())

                logHelper.log("RequestHeader: ${request.build().headers.joinToString()}")
                logHelper.log("ResponseBody: ${response.peekBody(Long.MAX_VALUE).string()}")

                response
            }
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideSplashService(client: OkHttpClient): BookService {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .baseUrl(KAKAO_BOOK_HOST)
            .build()
            .create(BookService::class.java)
    }
}