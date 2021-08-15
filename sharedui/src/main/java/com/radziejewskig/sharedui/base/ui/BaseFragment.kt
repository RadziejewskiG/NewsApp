package com.radziejewskig.sharedui.base.ui

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.radziejewskig.presentation.base.CommonEvent
import com.radziejewskig.presentation.base.CommonState
import com.radziejewskig.presentation.base.viewmodel.FragmentViewModel
import com.radziejewskig.sharedui.extension.*
import com.radziejewskig.sharedui.util.DialogDepository
import kotlinx.coroutines.delay
import javax.inject.Inject

abstract class BaseFragment<BaseState: CommonState, BaseEvent: CommonEvent>(@LayoutRes layoutRes: Int): Fragment(layoutRes) {

    abstract val viewModel: FragmentViewModel<BaseState, BaseEvent>

    abstract val binding: ViewBinding

    open fun provideSnackbarAnchorView(): View? = null

    fun ac(): BaseActivity? = (activity as BaseActivity?)

    fun canNavigate() = ac()?.canNavigate() ?: true

    @Inject
    lateinit var dialogDepository: DialogDepository

    open val isStatusBarLight: Boolean = true

    protected fun currentState(): BaseState = viewModel.currentState()

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupFragmentTransitions()

        getArgsData()

        viewModel.start()

        setupEvents()

        setupMessageEvent()

        viewModel.isProcessingData.collectLatestWhenStarted(viewLifecycleScope) { isProcessing ->
            if(isProcessing) {
                showLoadingDialog()
            } else{
                hideDialog()
            }
        }

        ac()?.navigationAndStatusBarHeight()?.collectLatestWhenStarted(viewLifecycleScope) { navigationAndStatusBarHeight ->
            windowInsetsChanged(navigationAndStatusBarHeight.first, navigationAndStatusBarHeight.second)
        }

        viewModel.state.collectLatestWhenStarted(viewLifecycleScope) { state ->
            handleState(state)
        }

    }

    open fun setupFragmentTransitions() {
        enterTransition = null
        exitTransition = null
        reenterTransition = null
        returnTransition = null
    }

    /**
     * Use this method to get the data from args and (when needed) pass it to vm
     */
    open fun getArgsData() = Unit

    private fun setupEvents() {
        viewModel.events.collectWhenStarted(viewLifecycleScope) {
            handleSingleEvent(it.getContentIfNotHandled())
        }
    }

    private fun setupMessageEvent() {
        viewModel.messageEvent.collectWhenStarted(viewLifecycleScope) {
            it.getContentIfNotHandled()?.let { data ->
                showMessage(data.messageData)
            }
        }
    }

    open fun handleState(state: BaseState) = Unit

    open fun handleSingleEvent(event: BaseEvent?) = Unit

    fun showLoadingDialog() {
        if(::dialogDepository.isInitialized) {
            dialogDepository.showLoadingDialog()
        }
    }

    fun hideDialog() {
        if(::dialogDepository.isInitialized) {
            dialogDepository.hideDialog()
        }
    }

    @CallSuper
    override fun onSaveInstanceState(outState: Bundle) {
        runCatching { viewModel.saveToBundle() }
        super.onSaveInstanceState(outState)
    }

    @CallSuper
    override fun onStart() {
        super.onStart()

        ac()?.setLightStatusBarAppearance(isStatusBarLight)

        ac()?.setLastNavComponentActionId(null)

        launchLifecycleScopeWhenStarted {
            delay(250)
            if(viewModel.isProcessingData.value) {
                showLoadingDialog()
            }
        }
    }

    open fun onBackPressed(): Boolean = false

    open fun windowInsetsChanged(navigationBarHeight: Int, statusBarHeight: Int) = Unit

    @CallSuper
    override fun onDestroyView() {
        hideDialog()
        super.onDestroyView()
    }

}
