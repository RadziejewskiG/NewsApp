package com.radziejewskig.presentation.base.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.radziejewskig.presentation.base.CommonEvent
import com.radziejewskig.presentation.base.CommonState
import com.radziejewskig.presentation.base.ShowMessageEvent
import com.radziejewskig.presentation.data.MessageData
import com.radziejewskig.presentation.data.MessageType
import com.radziejewskig.presentation.extensions.launchSafe
import com.radziejewskig.presentation.utils.EventWrapper
import com.radziejewskig.presentation.utils.SafeHandleDelegate
import com.radziejewskig.presentation.utils.helper.ErrorUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class FragmentViewModel<BaseState: CommonState, BaseEvent: CommonEvent>(stateHandle: SavedStateHandle): BaseViewModel<BaseState>(stateHandle) {

    private val _events = MutableSharedFlow<EventWrapper<BaseEvent>>(
        replay = 7,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val events = _events.asSharedFlow()

    private val _messageEvent = MutableSharedFlow<EventWrapper<ShowMessageEvent>>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val messageEvent = _messageEvent.asSharedFlow()

    fun BaseEvent.emit() {
        val event = this
        viewModelScope.launch {
            _events.emit(EventWrapper(event))
        }
    }

    private var argsPassed: Boolean by SafeHandleDelegate(stateHandle, "argsPassed", false)

    protected fun setArgsPassed(actions: () -> Unit) {
        if (!argsPassed) {
            argsPassed = true
            actions()
        }
    }

    protected fun launchLoading(
        dispatcher: CoroutineContext = Dispatchers.IO,
        showErrorMessages: Boolean = true,
        onError: (Throwable) -> Unit = {},
        content: suspend CoroutineScope.() -> Unit,
    ) = viewModelScope.launchSafe(
        dispatcher = dispatcher,
        content = {
            setIsProcessingData(true)
            content()
            setIsProcessingData(false)
        },
        onError = { error ->
            if(showErrorMessages) {
                showMessage(
                    MessageData(
                        MessageType.ERROR,
                        messageRes = ErrorUtil.getStringResForException(error)
                    )
                )
            }
            onError(error)
            setIsProcessingData(false)
        }
    )

    protected fun launch(
        dispatcher: CoroutineContext = Dispatchers.IO,
        showErrorMessages: Boolean = true,
        onError: (Throwable) -> Unit = {},
        content: suspend CoroutineScope.() -> Unit,
    ) = viewModelScope.launchSafe(
        dispatcher = dispatcher,
        content = content,
        onError = { error ->
            if(showErrorMessages) {
                showMessage(
                    MessageData(
                        MessageType.ERROR,
                        messageRes = ErrorUtil.getStringResForException(error)
                    )
                )
            }
            onError(error)
        }
    )

    protected fun showMessage(showMessageData: MessageData) {
        viewModelScope.launch {
            _messageEvent.emit(EventWrapper(ShowMessageEvent(showMessageData)))
        }
    }

}