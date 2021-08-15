package com.radziejewskig.presentation.extensions

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

fun CoroutineScope.launchSafe(
    dispatcher: CoroutineContext,
    onError: suspend CoroutineScope.(Throwable) -> Unit = {},
    content: suspend CoroutineScope.() -> Unit,
) = launch (
    dispatcher
) {
    try {
        content()
    } catch (e: Exception) {
        if(e is CancellationException) {
            throw e
        }
        onError(e)
    }
}
