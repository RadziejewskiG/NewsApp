package com.radziejewskig.data.model.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewsApiModel (
    val id: String,
    val publishedTime: Long,
    val headline: String,
    val body: String,
    @Json(name = "imageUrl")
    val urlImage: String?
): ApiModel
