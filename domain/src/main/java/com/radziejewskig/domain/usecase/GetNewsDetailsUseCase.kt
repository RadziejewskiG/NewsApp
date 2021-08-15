package com.radziejewskig.domain.usecase

import com.radziejewskig.domain.model.NewsDomainModel
import com.radziejewskig.domain.repository.NewsRepository
import javax.inject.Inject

class GetNewsDetailsUseCase
@Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(newsId: String): NewsDomainModel = newsRepository.getNewsDetails(newsId)
}
