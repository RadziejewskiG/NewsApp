package com.radziejewskig.sharedui.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

fun <T> Flow<T>.collectWhenStarted(
    scope: LifecycleCoroutineScope,
    content: suspend CoroutineScope.(T) -> Unit
) {
    scope.launchWhenStarted {
        collect {
            content(it)
        }
    }
}

fun <T> Flow<T>.collectLatestWhenStarted(
    scope: LifecycleCoroutineScope,
    content: suspend CoroutineScope.(T) -> Unit
) {
    scope.launchWhenStarted {
        collectLatest {
            content(it)
        }
    }
}

fun <T> Flow<T>.collectLatestWhenStartedAutoCancelling(
    lifecycleOwner: LifecycleOwner,
    content: suspend CoroutineScope.(T) -> Unit
) {
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            collectLatest {
                content(it)
            }
        }
    }
}

fun Fragment.launchLifecycleScopeWhenStarted(
    onError: (Throwable) -> Unit = {},
    content: suspend CoroutineScope.() -> Unit
) = viewLifecycleOwner.lifecycleScope.launchSafeWhenStarted(
    content = {
        content()
    },
    onError = { error ->
        onError(error)
    }
)


fun AppCompatActivity.launchLifecycleScopeWhenStarted(
    delayMilliseconds: Long = 0,
    onError: (Throwable) -> Unit = {},
    content: suspend CoroutineScope.() -> Unit,
) = lifecycleScope.launchSafeWhenStarted (
    content = {
        delay(delayMilliseconds)
        content()
    },
    onError = { error ->
        onError(error)
    }
)

fun LifecycleCoroutineScope.launchSafeWhenStarted(
    onError: (Throwable) -> Unit = {},
    content: suspend CoroutineScope.() -> Unit,
) = launchWhenStarted {
    try {
        content()
    } catch (e: Exception) {
        if(e is CancellationException) {
            throw e
        }
        onError(e)
    }
}
