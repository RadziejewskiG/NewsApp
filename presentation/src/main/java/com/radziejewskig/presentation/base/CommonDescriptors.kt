package com.radziejewskig.presentation.base

import android.os.Parcelable
import com.radziejewskig.presentation.data.MessageData
import kotlinx.parcelize.Parcelize

@Parcelize
open class CommonState: Parcelable

interface CommonEvent

class ShowMessageEvent(
    val messageData: MessageData
)