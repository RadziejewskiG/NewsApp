package com.radziejewskig.data.mapper

import com.radziejewskig.data.base.ApiToDomainMapper
import com.radziejewskig.data.model.api.NewsApiModel
import com.radziejewskig.domain.model.NewsDomainModel

class NewsApiToDomainMapper:
    ApiToDomainMapper<NewsApiModel, NewsDomainModel>() {

    override fun map(input: NewsApiModel) = NewsDomainModel (
        id = input.id,
        isRead = false,
        publishedTime = input.publishedTime,
        headline = input.headline,
        body = input.body,
        urlImage = input.urlImage.orEmpty()
    )
}
