package com.radziejewskig.newsapp.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.forEach
import androidx.core.view.updateLayoutParams
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.radziejewskig.newsapp.R
import com.radziejewskig.newsapp.databinding.ActivityMainBinding
import com.radziejewskig.presentation.viewmodels.MainActivityViewModel
import com.radziejewskig.sharedui.base.ui.BaseActivity
import com.radziejewskig.sharedui.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: BaseActivity() {

    override val viewModel: MainActivityViewModel by viewModels()

    override val binding by viewBinding(ActivityMainBinding::inflate)

    override val mainNavHostFragmentId = R.id.mainNavHostFragment

    override fun themeToOverride(): Int = R.style.Theme_App_TranslucentStatus

    private var bottomNavPageDestinationsTitlesWithIds: List<Pair<String, Int>> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        binding.run {
            val navHostFragment = supportFragmentManager.findFragmentById(mainNavHostFragmentId) as NavHostFragment
            val navController = navHostFragment.navController
            bottomNavigationView.setupWithNavController(navController)
            bottomNavigationView.setOnItemReselectedListener(null)
            setupBottomDestinations()
        }
    }

    private fun setupBottomDestinations() {
        val bottomDestinations = mutableListOf<Pair<String, Int>>()
        binding.bottomNavigationView.menu.forEach { item ->
            bottomDestinations.add(item.title.toString() to item.itemId)
        }
        bottomNavPageDestinationsTitlesWithIds = bottomDestinations
    }

    override fun navigationHandlesBack(): Boolean {
        val currentDestinationLabel = findNavController(mainNavHostFragmentId).currentDestination?.label?.toString().orEmpty()
        val defaultNavTab = bottomNavPageDestinationsTitlesWithIds.firstOrNull()
        return if(currentDestinationLabel.isNotEmpty() && currentDestinationLabel in bottomNavPageDestinationsTitlesWithIds.map { it.first }) {
            defaultNavTab?.let {
                val defaultTabLabel = defaultNavTab.first
                val defaultTabMenuItemId = defaultNavTab.second
                if(currentDestinationLabel != defaultTabLabel) {
                    binding.bottomNavigationView.selectedItemId = defaultTabMenuItemId
                    true
                } else {
                    false
                }
            } ?: false
        } else {
            findNavController(mainNavHostFragmentId).navigateUp()
        }
    }

    override fun windowInsetsChanged(navigationBarHeight: Int, statusBarHeight: Int) {
        super.windowInsetsChanged(navigationBarHeight, statusBarHeight)
        binding.bottomAppBar.updateLayoutParams {
            height = resources.getDimensionPixelSize(R.dimen.bottom_app_bar_height) + navigationBarHeight
        }
        binding.bottomNavigationView.updateLayoutParams {
            height = resources.getDimensionPixelSize(R.dimen.bottom_app_bar_height) + navigationBarHeight
        }
    }

}
