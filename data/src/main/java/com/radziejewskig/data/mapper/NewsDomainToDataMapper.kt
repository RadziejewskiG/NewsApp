package com.radziejewskig.data.mapper

import com.radziejewskig.data.base.DomainToDataMapper
import com.radziejewskig.data.model.database.entity.NewsEntity
import com.radziejewskig.domain.model.NewsDomainModel

class NewsDomainToDataMapper:
    DomainToDataMapper<NewsDomainModel, NewsEntity>() {

    override fun map(input: NewsDomainModel) = NewsEntity (
        newsId = input.id,
        isRead = input.isRead,
        publishedTime = input.publishedTime,
        headline = input.headline,
        body = input.body,
        urlImage = input.urlImage
    )
}