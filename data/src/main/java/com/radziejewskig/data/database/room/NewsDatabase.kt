package com.radziejewskig.data.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.radziejewskig.data.database.room.dao.NewsDao
import com.radziejewskig.data.database.room.dao.NewsSavedReadStatusDao
import com.radziejewskig.data.model.database.entity.NewsEntity
import com.radziejewskig.data.model.database.entity.NewsSavedReadStatusEntity

@Database(
    entities = [
        NewsEntity::class,
        NewsSavedReadStatusEntity::class
    ],
    version = 1
)
abstract class NewsDatabase: RoomDatabase() {

    abstract fun newsDao(): NewsDao

    abstract fun newsSavedReadStatusDao(): NewsSavedReadStatusDao

}
