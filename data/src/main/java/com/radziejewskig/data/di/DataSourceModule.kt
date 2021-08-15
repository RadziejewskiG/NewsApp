package com.radziejewskig.data.di

import com.footballco.recruitment.MatchesApiFake
import com.radziejewskig.data.database.room.NewsDatabase
import com.radziejewskig.data.database.room.dao.NewsDao
import com.radziejewskig.data.database.room.dao.NewsSavedReadStatusDao
import com.radziejewskig.data.datasource.matches.MatchesRemoteDataSource
import com.radziejewskig.data.datasource.news.NewsLocalDataSource
import com.radziejewskig.data.datasource.news.NewsRemoteDataSource
import com.radziejewskig.data.framework.retrofit.NewsApiService
import com.radziejewskig.data.mapper.MatchSchemaApiToDomainMapper
import com.radziejewskig.data.mapper.NewsApiToDomainMapper
import com.radziejewskig.data.mapper.NewsDomainToDataMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class DataSourceModule {

    @Provides
    @Singleton
    fun provideNewsRemoteDataSource(
        newsApiService: NewsApiService,
        newsApiToDomainMapper: NewsApiToDomainMapper
    ) = NewsRemoteDataSource(newsApiService, newsApiToDomainMapper)

    @Provides
    @Singleton
    fun provideNewsLocalDataSource(
        db: NewsDatabase,
        newsDao: NewsDao,
        newsSavedReadStatusDao: NewsSavedReadStatusDao,
        newsDomainToDataMapper: NewsDomainToDataMapper,
    ) = NewsLocalDataSource(
        db,
        newsDao,
        newsSavedReadStatusDao,
        newsDomainToDataMapper
    )

    @Provides
    @Singleton
    fun provideMatchesRemoteDataSource(
        matchesApiFake: MatchesApiFake,
        matchSchemaApiToDomainMapper: MatchSchemaApiToDomainMapper
    ) = MatchesRemoteDataSource(
        matchesApiFake,
        matchSchemaApiToDomainMapper
    )

}
