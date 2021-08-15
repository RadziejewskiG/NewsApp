package com.radziejewskig.sharedui.base.ui

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.addCallback
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.OnApplyWindowInsetsListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.viewbinding.ViewBinding
import com.radziejewskig.presentation.base.CommonState
import com.radziejewskig.presentation.base.viewmodel.ActivityViewModel
import com.radziejewskig.sharedui.extension.collectLatestWhenStarted
import com.radziejewskig.sharedui.extension.hideKeyboard
import com.radziejewskig.sharedui.extension.launchLifecycleScopeWhenStarted
import com.radziejewskig.sharedui.util.DialogDepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import javax.inject.Inject

abstract class BaseActivity: AppCompatActivity() {

    abstract val viewModel: ActivityViewModel<out CommonState>

    abstract val binding: ViewBinding

    abstract val mainNavHostFragmentId: Int

    @Inject
    lateinit var dialogDepository: DialogDepository

    fun navigationAndStatusBarHeight() = viewModel.navigationAndStatusBarHeight

    open fun themeToOverride(): Int = 0

    @CallSuper
    open fun windowInsetsChanged(navigationBarHeight: Int, statusBarHeight: Int) {
        viewModel.navigationAndStatusBarHeight.value = navigationBarHeight to statusBarHeight
    }

    var isKeyboardOpened = false
        private set

    var lastNavComponentActionId: Int? = null
        private set

    fun canNavigate(): Boolean = viewModel.canNavigate.value

    private var enableBackPressJob: Job? = null

    fun setLastNavComponentActionId(id: Int?) {
        lastNavComponentActionId = id
    }

    fun setCanNavigate(canNavigate: Boolean, delayToTurnBackOn: Long = 1000) {
        enableBackPressJob?.cancel()
        viewModel.setCanNavigate(canNavigate)
        if(!canNavigate) {
            enableBackPressJob = launchLifecycleScopeWhenStarted {
                delay(delayToTurnBackOn)
                viewModel.setCanNavigate(true)
            }
        }
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        val theme = themeToOverride()
        if(theme > 0) {
            setTheme(theme)
        }

        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        viewModel.setCanNavigate(true)

        setupBackPressDispatcher()

        viewModel.isStatusBarLight.collectLatestWhenStarted(lifecycleScope) { isLight ->
            WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = isLight
        }

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        viewModel.start()

        setupWindowInsetsListener()

        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    private fun setupBackPressDispatcher() {
        onBackPressedDispatcher.addCallback(this) {
            if(canNavigate()) {
                val fragmentHandlesBack = try {
                    val currentFragment = supportFragmentManager.findFragmentById(mainNavHostFragmentId)?.childFragmentManager?.fragments?.get(0)
                    try {
                        (currentFragment as BaseFragment<*, *>?)?.onBackPressed() ?: false
                    } catch (e: Exception) {
                        false
                    }
                } catch (e: Exception) {
                    false
                }

                if (!fragmentHandlesBack) {
                    if (!navigationHandlesBack()) {
                        if(!onBackPress()) {
                            // Simulate Home button click
                            val intent = Intent(Intent.ACTION_MAIN).apply {
                                addCategory(Intent.CATEGORY_HOME)
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            }
                            startActivity(intent)
                        }
                    }
                }
            }
        }
    }

    private fun setupWindowInsetsListener() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root, OnApplyWindowInsetsListener { v, insets ->
            val navigationBarHeight: Int =
                insets?.getInsets(WindowInsetsCompat.Type.systemBars())?.bottom ?: 0

            val statusBarHeight: Int =
                insets?.getInsets(WindowInsetsCompat.Type.systemBars())?.top ?: 0

            isKeyboardOpened = insets?.isVisible(WindowInsetsCompat.Type.ime()) ?: false

            windowInsetsChanged(navigationBarHeight, statusBarHeight)

            return@OnApplyWindowInsetsListener insets
        })
    }

    open fun navigationHandlesBack(): Boolean = findNavController(mainNavHostFragmentId).navigateUp()

    open fun onBackPress(): Boolean = false

    @CallSuper
    override fun onStop() {
        if(::dialogDepository.isInitialized) {
            dialogDepository.hideDialog()
        }
        hideKeyboard()
        super.onStop()
    }

    @CallSuper
    override fun onSaveInstanceState(outState: Bundle) {
        runCatching { viewModel.saveToBundle() }
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        enableBackPressJob?.cancel()
        enableBackPressJob = null
        super.onDestroy()
    }

}
