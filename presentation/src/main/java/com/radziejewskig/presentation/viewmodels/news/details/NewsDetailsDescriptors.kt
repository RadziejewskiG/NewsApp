package com.radziejewskig.presentation.viewmodels.news.details

import com.radziejewskig.presentation.base.CommonState
import com.radziejewskig.presentation.model.NewsPresentationModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsDetailsState(
    val isLoadingNews: Boolean,
    val news: NewsPresentationModel? = null
): CommonState()