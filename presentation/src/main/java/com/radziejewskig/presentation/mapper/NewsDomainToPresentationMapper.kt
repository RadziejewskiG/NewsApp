package com.radziejewskig.presentation.mapper

import com.radziejewskig.domain.model.NewsDomainModel
import com.radziejewskig.presentation.base.DomainToPresentationMapper
import com.radziejewskig.presentation.model.NewsPresentationModel

class NewsDomainToPresentationMapper:
    DomainToPresentationMapper<NewsDomainModel, NewsPresentationModel>() {
    override fun map(input: NewsDomainModel) = NewsPresentationModel (
        id = input.id,
        isRead = input.isRead,
        publishedTime = input.publishedTime,
        headline = input.headline,
        body = input.body,
        urlImage = input.urlImage
    )
}
