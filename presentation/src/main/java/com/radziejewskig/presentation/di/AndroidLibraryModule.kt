package com.radziejewskig.presentation.di

import androidx.paging.ExperimentalPagingApi
import com.radziejewskig.domain.usecase.DeleteAllNewsUseCase
import com.radziejewskig.domain.usecase.GetNewsPagedUseCase
import com.radziejewskig.domain.usecase.PutOrUpdateNewsUseCase
import com.radziejewskig.presentation.mediator.NewsRemoteMediator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AndroidLibraryModule {

    @ExperimentalPagingApi
    @Provides
    @Singleton
    fun provideNewsRemoteMediator(
        getNewsPagedUseCase: GetNewsPagedUseCase,
        putOrUpdateNewsUseCase: PutOrUpdateNewsUseCase,
        deleteAllNewsUseCase: DeleteAllNewsUseCase
    ) = NewsRemoteMediator(
        getNewsPagedUseCase,
        putOrUpdateNewsUseCase,
        deleteAllNewsUseCase
    )

}
