package com.radziejewskig.presentation.di

import com.radziejewskig.presentation.mapper.MatchSchemaDomainToPresentationMapper
import com.radziejewskig.presentation.mapper.NewsDomainToPresentationMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class PresentationMapperModule {

    @Provides
    @Singleton
    fun provideNewsDomainToPresentationMapper() = NewsDomainToPresentationMapper()

    @Provides
    @Singleton
    fun provideMatchSchemaDomainToPresentationMapper() = MatchSchemaDomainToPresentationMapper()

}
