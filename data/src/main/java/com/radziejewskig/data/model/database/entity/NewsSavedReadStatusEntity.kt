package com.radziejewskig.data.model.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.radziejewskig.data.model.database.DataModel

@Entity(tableName = "news_saved")
data class NewsSavedReadStatusEntity (

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "newsId")
    var newsId: String,

): DataModel