package com.radziejewskig.domain.usecase

import com.radziejewskig.domain.repository.NewsRepository
import javax.inject.Inject

class DeleteAllNewsUseCase
@Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(): Boolean = newsRepository.deleteAllNews()
}
