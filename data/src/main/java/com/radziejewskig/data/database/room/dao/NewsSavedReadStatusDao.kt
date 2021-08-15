package com.radziejewskig.data.database.room.dao

import androidx.room.*
import com.radziejewskig.data.model.database.entity.NewsSavedReadStatusEntity

@Dao
interface NewsSavedReadStatusDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(news: NewsSavedReadStatusEntity): Long

    @Query("SELECT * FROM news_saved WHERE news_saved.newsId = :newsId")
    suspend fun getNewsSavedReadStatusByNewsId(newsId: String): NewsSavedReadStatusEntity?

}
