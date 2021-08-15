package com.radziejewskig.data.repository

import androidx.paging.PagingSource
import com.radziejewskig.data.datasource.news.NewsLocalDataSource
import com.radziejewskig.data.datasource.news.NewsRemoteDataSource
import com.radziejewskig.domain.model.NewsDomainModel
import com.radziejewskig.domain.repository.NewsRepository

class NewsRepositoryImpl(
    private val newsLocalDataSource: NewsLocalDataSource,
    private val newsRemoteDataSource: NewsRemoteDataSource
): NewsRepository {

    override suspend fun putOrUpdateNews(newsList: List<NewsDomainModel>): Boolean {
        return newsLocalDataSource.putOrUpdateNews(newsList)
    }

    override suspend fun updateNewsReadStatusByNewsId(newsId: String): Boolean {
        return newsLocalDataSource.updateNewsReadStatusByNewsId(newsId)
    }

    override fun getNewsPagingSource(): PagingSource<Int, NewsDomainModel> {
        return newsLocalDataSource.getNewsPagingSource()
    }

    override suspend fun deleteAllNews(): Boolean {
        return newsLocalDataSource.deleteAllNews()
    }

    override suspend fun getNewsPaged(page: Int): List<NewsDomainModel> {
        return newsRemoteDataSource.getNewsPaged(page)
    }

    override suspend fun getNewsDetails(newsId: String): NewsDomainModel {
        return newsRemoteDataSource.getNewsDetails(newsId)
    }

}
