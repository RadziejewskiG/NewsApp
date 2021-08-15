package com.radziejewskig.data.datasource.news

import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.radziejewskig.data.database.room.NewsDatabase
import com.radziejewskig.data.database.room.dao.NewsDao
import com.radziejewskig.data.database.room.dao.NewsSavedReadStatusDao
import com.radziejewskig.data.mapper.NewsDomainToDataMapper
import com.radziejewskig.data.model.database.entity.NewsSavedReadStatusEntity
import com.radziejewskig.domain.model.NewsDomainModel

class NewsLocalDataSource(
    private val db: NewsDatabase,
    private val newsDao: NewsDao,
    private val newsSavedReadStatusDao: NewsSavedReadStatusDao,
    private val newsDomainToDataMapper: NewsDomainToDataMapper,
) {

    suspend fun putOrUpdateNews(newsList: List<NewsDomainModel>): Boolean = db.withTransaction {
        newsList.forEach { news ->
            val wasNewsRead = newsSavedReadStatusDao.getNewsSavedReadStatusByNewsId(news.id) != null
            val existingNews = newsDao.getNewsByNewsId(news.id)
            val newNews = newsDomainToDataMapper.toData(news)

            val newsToInsert = existingNews?.copy(
                publishedTime = newNews.publishedTime,
                headline = newNews.headline,
                body = newNews.body,
                urlImage = newNews.urlImage,
                isRead = wasNewsRead,
            ) ?: newNews.copy(isRead = wasNewsRead)

            newsDao.insert(newsToInsert)
        }

        true
    }

    suspend fun updateNewsReadStatusByNewsId(newsId: String): Boolean = db.withTransaction {
        val wasSaved = newsSavedReadStatusDao.getNewsSavedReadStatusByNewsId(newsId) != null
        val existingNews = newsDao.getNewsByNewsId(newsId)
        if(!wasSaved) {
            newsSavedReadStatusDao.insert(NewsSavedReadStatusEntity(newsId = newsId))
        }
        if(existingNews == null || !existingNews.isRead) {
            newsDao.updateNewsReadStatusByNewsId(newsId)
        }
        true
    }

    fun getNewsPagingSource(): PagingSource<Int, NewsDomainModel> {
        return newsDao.getNewsPagingSource()
    }

    suspend fun deleteAllNews(): Boolean {
        newsDao.deleteAllNews()
        return true
    }

}
