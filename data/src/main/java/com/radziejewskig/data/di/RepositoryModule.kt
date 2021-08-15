package com.radziejewskig.data.di

import com.radziejewskig.data.datasource.matches.MatchesRemoteDataSource
import com.radziejewskig.data.datasource.news.NewsLocalDataSource
import com.radziejewskig.data.datasource.news.NewsRemoteDataSource
import com.radziejewskig.data.repository.MatchesRepositoryImpl
import com.radziejewskig.data.repository.NewsRepositoryImpl
import com.radziejewskig.domain.repository.MatchesRepository
import com.radziejewskig.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class RepositoryModule {

    @Provides
    @Singleton
    fun provideNewsRepository(
        newsLocalDataSource: NewsLocalDataSource,
        newsRemoteDataSource: NewsRemoteDataSource
    ): NewsRepository = NewsRepositoryImpl(
        newsLocalDataSource,
        newsRemoteDataSource
    )

    @Provides
    @Singleton
    fun provideMatchesRepository(
        matchesRemoteDataSource: MatchesRemoteDataSource
    ): MatchesRepository = MatchesRepositoryImpl(matchesRemoteDataSource)

}
