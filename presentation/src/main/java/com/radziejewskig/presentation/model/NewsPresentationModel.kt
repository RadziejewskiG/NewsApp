package com.radziejewskig.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsPresentationModel (
    val id: String,
    val isRead: Boolean,
    val publishedTime: Long,
    val headline: String,
    val body: String,
    val urlImage: String?
): PresentationModel, Parcelable
