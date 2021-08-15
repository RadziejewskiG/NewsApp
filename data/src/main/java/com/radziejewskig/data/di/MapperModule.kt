package com.radziejewskig.data.di

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
internal class MapperModule {

    @Provides
    @Singleton
    fun provideNewsApiToDomainMapper() = NewsApiToDomainMapper()

    @Provides
    @Singleton
    fun provideNewsDomainToDataMapper() = NewsDomainToDataMapper()

    @Provides
    @Singleton
    fun provideMatchSchemaApiToDomainMapper() = MatchSchemaApiToDomainMapper()

}
