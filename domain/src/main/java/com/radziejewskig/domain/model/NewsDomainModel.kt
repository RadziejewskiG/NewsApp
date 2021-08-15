package com.radziejewskig.domain.model

data class NewsDomainModel (
    val id: String,
    val isRead: Boolean,
    val publishedTime: Long,
    val headline: String,
    val body: String,
    val urlImage: String
): DomainModel
