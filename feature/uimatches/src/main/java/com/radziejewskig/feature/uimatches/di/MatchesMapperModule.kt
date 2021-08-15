package com.radziejewskig.feature.uimatches.di

import com.radziejewskig.feature.uimatches.mapper.MatchSocketUpdateToPresentationMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class MatchesMapperModule {

    @Provides
    @Singleton
    fun providesMatchSocketUpdateToPresentationMapper() = MatchSocketUpdateToPresentationMapper()

}
