package com.radziejewskig.presentation.viewmodels.news.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.radziejewskig.domain.model.NewsDomainModel
import com.radziejewskig.domain.usecase.GetNewsPagingSourceUseCase
import com.radziejewskig.domain.usecase.UpdateNewsReadStatusByNewsIdUseCase
import com.radziejewskig.presentation.base.CommonEvent
import com.radziejewskig.presentation.base.CommonState
import com.radziejewskig.presentation.base.viewmodel.FragmentViewModel
import com.radziejewskig.presentation.mapper.NewsDomainToPresentationMapper
import com.radziejewskig.presentation.mediator.NEWS_PAGE_SIZE
import com.radziejewskig.presentation.mediator.NewsRemoteMediator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class NewsListViewModel @Inject constructor (
    private val handle: SavedStateHandle,
    private val updateNewsReadStatusByNewsIdUseCase: UpdateNewsReadStatusByNewsIdUseCase,
    private val getNewsPagingSourceUseCase: GetNewsPagingSourceUseCase,
    private val newsRemoteMediator: NewsRemoteMediator,
    private val newsDomainToPresentationMapper: NewsDomainToPresentationMapper,
): FragmentViewModel<CommonState, CommonEvent>(handle) {

    override fun setupInitialState() = CommonState()

    val news = Pager(
        config = PagingConfig(
            pageSize = NEWS_PAGE_SIZE,
            initialLoadSize = NEWS_PAGE_SIZE,
            prefetchDistance = 1,
        ),
        remoteMediator = newsRemoteMediator
    ) {
        (getNewsPagingSourceUseCase() as PagingSource<Int, NewsDomainModel>)
    }.flow
     .map { pagingData ->
         pagingData.map {
             newsDomainToPresentationMapper.toPresentation(it)
         }
     }
     .cachedIn(viewModelScope)

    fun newsClicked(newsId: String) {
        launch {
            updateNewsReadStatusByNewsIdUseCase(newsId)
        }
    }

}
