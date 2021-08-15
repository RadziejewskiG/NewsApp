package com.radziejewskig.presentation.base.viewmodel

import androidx.annotation.CallSuper
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.radziejewskig.presentation.base.CommonState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.concurrent.atomic.AtomicBoolean

abstract class BaseViewModel<State: CommonState>(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private val initialState: State by lazy { setupInitialState() }
    protected abstract fun setupInitialState(): State

    private var _state = MutableStateFlow(savedStateHandle.get<State>(STATE_BUNDLE_TAG) ?: initialState)

    val state = _state.asStateFlow()

    fun currentState(): State = state.value

    private val isInitialized = AtomicBoolean(false)

    // Indicator for loading dialog
    private val _isProcessingData = MutableStateFlow(false)
    val isProcessingData = _isProcessingData.asStateFlow()

    fun setIsProcessingData(isProcessing: Boolean) {
        _isProcessingData.value = isProcessing
    }

    @CallSuper
    open fun start() {
        if (isInitialized.compareAndSet(false, true)) {
            initialAct()
        }
    }

    protected open fun initialAct() = Unit

    @CallSuper
    open fun saveToBundle() {
        savedStateHandle.set(STATE_BUNDLE_TAG, currentState())
    }

    protected fun mutateState(mutation: State.() -> State) {
        // Atomic update for MutableStateFlow
        _state.update { currentState().mutation() }
    }

    companion object {
        private const val STATE_BUNDLE_TAG: String = "viewModelState"
    }

}