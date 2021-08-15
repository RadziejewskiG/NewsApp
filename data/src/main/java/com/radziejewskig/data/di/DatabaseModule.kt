package com.radziejewskig.data.di

import android.content.Context
import androidx.room.Room
import com.radziejewskig.data.BuildConfig
import com.radziejewskig.data.database.room.NewsDatabase
import com.radziejewskig.data.database.room.dao.NewsDao
import com.radziejewskig.data.database.room.dao.NewsSavedReadStatusDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class DatabaseModule {

    @Singleton
    @Provides
    fun provideNewsDatabase(
        @ApplicationContext appContext: Context
    ): NewsDatabase =
        Room.databaseBuilder(
            appContext,
            NewsDatabase::class.java,
            BuildConfig.DB_NAME
        ).build()

    @Provides
    fun provideNewsDao(
        database: NewsDatabase
    ): NewsDao {
        return database.newsDao()
    }

    @Provides
    fun provideNewsSavedReadStatusDao(
        database: NewsDatabase
    ): NewsSavedReadStatusDao {
        return database.newsSavedReadStatusDao()
    }

}
