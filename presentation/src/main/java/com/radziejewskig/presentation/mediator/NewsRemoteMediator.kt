package com.radziejewskig.presentation.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.radziejewskig.domain.model.NewsDomainModel
import com.radziejewskig.domain.usecase.DeleteAllNewsUseCase
import com.radziejewskig.domain.usecase.GetNewsPagedUseCase
import com.radziejewskig.domain.usecase.PutOrUpdateNewsUseCase
import kotlinx.coroutines.delay

@ExperimentalPagingApi
class NewsRemoteMediator(
    private val getNewsPagedUseCase: GetNewsPagedUseCase,
    private val putOrUpdateNewsUseCase: PutOrUpdateNewsUseCase,
    private val deleteAllNewsUseCase: DeleteAllNewsUseCase
) : RemoteMediator<Int, NewsDomainModel>() {

    private var currentPage = 1

    private var wasListUpdated: Boolean = false

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, NewsDomainModel>
    ): MediatorResult {

        val isAppending = loadType == LoadType.APPEND

        // Fake delay to show bottom progress bar for some time
        delay(250)

        return try {
            val nextPage = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    wasListUpdated = false
                    currentPage + 1
                }
            }
            currentPage = nextPage

            val newsList = getNewsPagedUseCase(currentPage)

            if (loadType == LoadType.REFRESH) {
                deleteAllNewsUseCase()
            }

            putOrUpdateNewsUseCase(newsList)

            wasListUpdated = true

            if(newsList.isEmpty() && isAppending) {
                currentPage --
            }

            MediatorResult.Success(endOfPaginationReached = newsList.size < NEWS_PAGE_SIZE)
        } catch (e: Exception) {
            if(!wasListUpdated && isAppending) {
                currentPage --
            }
            MediatorResult.Error(e)
        }
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

}

const val NEWS_PAGE_SIZE = 20
