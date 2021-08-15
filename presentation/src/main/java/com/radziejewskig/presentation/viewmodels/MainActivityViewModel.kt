package com.radziejewskig.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import com.radziejewskig.presentation.base.CommonState
import com.radziejewskig.presentation.base.viewmodel.ActivityViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor (
    private val handle: SavedStateHandle
): ActivityViewModel<CommonState>(handle) {
    override fun setupInitialState() = CommonState()
}
