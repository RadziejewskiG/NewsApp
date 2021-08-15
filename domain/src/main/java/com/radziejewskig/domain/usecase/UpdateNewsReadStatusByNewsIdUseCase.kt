package com.radziejewskig.domain.usecase

import com.radziejewskig.domain.repository.NewsRepository
import javax.inject.Inject

class UpdateNewsReadStatusByNewsIdUseCase
@Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(newsId: String): Boolean = newsRepository.updateNewsReadStatusByNewsId(newsId)
}
