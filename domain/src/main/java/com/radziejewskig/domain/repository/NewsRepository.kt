package com.radziejewskig.domain.repository

import com.radziejewskig.domain.model.NewsDomainModel

interface NewsRepository {

    suspend fun getNewsPaged(page: Int): List<NewsDomainModel>

    suspend fun updateNewsReadStatusByNewsId(newsId: String): Boolean

    suspend fun putOrUpdateNews(newsList: List<NewsDomainModel>): Boolean

    suspend fun getNewsDetails(newsId: String): NewsDomainModel

    // It must be Any(sadly) and not the actual PagingSource because the domain layer must not know about the Paging library.
    fun getNewsPagingSource(): Any

    suspend fun deleteAllNews(): Boolean

}
