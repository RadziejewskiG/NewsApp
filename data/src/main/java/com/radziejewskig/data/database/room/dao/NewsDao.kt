package com.radziejewskig.data.database.room.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.radziejewskig.data.model.database.entity.NewsEntity
import com.radziejewskig.domain.model.NewsDomainModel

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(news: NewsEntity): Long

    @Query("UPDATE news SET isRead = 1 WHERE news.newsId = :newsId")
    suspend fun updateNewsReadStatusByNewsId(newsId: String): Int

    @Query("SELECT * FROM news WHERE news.newsId = :newsId")
    suspend fun getNewsByNewsId(newsId: String): NewsEntity?

    @Query("""
        SELECT
        news.newsId AS id,
        news.isRead AS isRead,
        news.publishedTime AS publishedTime,
        news.headline AS headline,
        news.body AS body,
        news.urlImage as urlImage
        FROM news 
        ORDER BY news.publishedTime DESC
    """)
    fun getNewsPagingSource(): PagingSource<Int, NewsDomainModel>

    @Query("DELETE FROM news")
    suspend fun deleteAllNews()

}
