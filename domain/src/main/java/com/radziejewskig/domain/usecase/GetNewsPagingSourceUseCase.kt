package com.radziejewskig.domain.usecase

import com.radziejewskig.domain.repository.NewsRepository
import javax.inject.Inject

class GetNewsPagingSourceUseCase
@Inject constructor(
    private val newsRepository: NewsRepository
) {
    // It must be Any(sadly) and not the actual PagingSource because the domain layer must not know about the Paging library.
    operator fun invoke(): Any = newsRepository.getNewsPagingSource()
}
