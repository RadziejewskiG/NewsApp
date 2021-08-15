package com.radziejewskig.domain.usecase

import com.radziejewskig.domain.model.NewsDomainModel
import com.radziejewskig.domain.repository.NewsRepository
import javax.inject.Inject

class PutOrUpdateNewsUseCase
@Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(newsList: List<NewsDomainModel>): Boolean = newsRepository.putOrUpdateNews(newsList)
}
