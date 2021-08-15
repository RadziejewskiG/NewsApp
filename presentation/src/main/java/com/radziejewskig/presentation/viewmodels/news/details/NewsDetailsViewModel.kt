package com.radziejewskig.presentation.viewmodels.news.details

import androidx.lifecycle.SavedStateHandle
import com.radziejewskig.domain.usecase.GetNewsDetailsUseCase
import com.radziejewskig.presentation.base.CommonEvent
import com.radziejewskig.presentation.base.viewmodel.FragmentViewModel
import com.radziejewskig.presentation.mapper.NewsDomainToPresentationMapper
import com.radziejewskig.presentation.utils.SafeHandleDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class NewsDetailsViewModel @Inject constructor (
    private val handle: SavedStateHandle,
    private val getNewsDetailsUseCase : GetNewsDetailsUseCase,
    private val newsDomainToPresentationMapper: NewsDomainToPresentationMapper
): FragmentViewModel<NewsDetailsState, CommonEvent>(handle) {

    private var newsId: String by SafeHandleDelegate(handle,"newsId", "")

    override fun setupInitialState() = NewsDetailsState(
        isLoadingNews = true
    )

    override fun initialAct() {
        loadNewsDetails()
    }

    private fun loadNewsDetails() {
        launch {
            mutateState {
                copy(isLoadingNews = true)
            }

            // Fake delay to show shimmer effect for some time
            delay(500)

            val newsDetails = getNewsDetailsUseCase(newsId)
            mutateState {
                copy(
                    isLoadingNews = false,
                    news = newsDomainToPresentationMapper.toPresentation(newsDetails)
                )
            }
        }
    }

    fun setup(newsId: String) = setArgsPassed {
        this.newsId = newsId
    }

}
