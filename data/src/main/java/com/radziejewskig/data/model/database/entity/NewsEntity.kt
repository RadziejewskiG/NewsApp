package com.radziejewskig.data.model.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.radziejewskig.data.model.database.DataModel

@Entity(tableName = "news")
data class NewsEntity (

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "isRead")
    val isRead: Boolean = false,

    @ColumnInfo(name = "newsId")
    var newsId: String,

    @ColumnInfo(name = "publishedTime")
    val publishedTime: Long,

    @ColumnInfo(name = "headline")
    val headline: String,

    @ColumnInfo(name = "body")
    val body: String,

    @ColumnInfo(name = "urlImage")
    val urlImage: String

): DataModel
