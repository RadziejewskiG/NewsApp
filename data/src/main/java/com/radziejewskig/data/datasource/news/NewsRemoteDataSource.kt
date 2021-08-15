package com.radziejewskig.data.datasource.news

import com.radziejewskig.data.framework.retrofit.NewsApiService
import com.radziejewskig.data.mapper.NewsApiToDomainMapper
import com.radziejewskig.domain.exception.NewsRepositoryException
import com.radziejewskig.domain.model.NewsDomainModel

class NewsRemoteDataSource (
    private val newsApiService: NewsApiService,
    private val newsApiToDomainMapper: NewsApiToDomainMapper
) {

    suspend fun getNewsPaged(page: Int): List<NewsDomainModel> {
        val result = newsApiService.getArticlesPaged(page)
        val data = result.body() ?: listOf()
        return data.map { newsApiToDomainMapper.toDomain(it) }
    }

    suspend fun getNewsDetails(newsId: String): NewsDomainModel {
        val result = newsApiService.getArticleDetails(newsId)
        val data = result.body()
        if (!result.isSuccessful || data == null) {
            throw NewsRepositoryException("Could not GET news details")
        }
        return newsApiToDomainMapper.toDomain(data)
    }

}
