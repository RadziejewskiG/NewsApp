package com.radziejewskig.presentation.base.viewmodel

import androidx.annotation.CallSuper
import androidx.lifecycle.SavedStateHandle
import com.radziejewskig.presentation.base.CommonState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class ActivityViewModel<BaseState: CommonState>(private val stateHandle: SavedStateHandle): BaseViewModel<BaseState>(stateHandle) {

    private val _canNavigate = MutableStateFlow(stateHandle.get("canNavigate") ?: true)
    val canNavigate = _canNavigate.asStateFlow()

    private val _isStatusBarLight = MutableStateFlow(stateHandle.get("isStatusBarLight") ?: true)
    val isStatusBarLight = _isStatusBarLight.asStateFlow()

    val navigationAndStatusBarHeight = MutableStateFlow(Pair(0, 0))

    fun setCanNavigate(navigatingEnabled: Boolean) {
        _canNavigate.value = navigatingEnabled
    }

    fun setIsStatusBarLight(isLight: Boolean) {
        _isStatusBarLight.value = isLight
    }

    @CallSuper
    override fun saveToBundle() {
        super.saveToBundle()
        stateHandle.set("isStatusBarLight", isStatusBarLight.value)
        stateHandle.set("canNavigate", canNavigate.value)
    }

}