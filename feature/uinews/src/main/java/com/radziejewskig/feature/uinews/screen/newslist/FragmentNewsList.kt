package com.radziejewskig.feature.uinews.screen.newslist

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.radziejewskig.feature.uinews.R
import com.radziejewskig.feature.uinews.databinding.FragmentNewsListBinding
import com.radziejewskig.presentation.base.CommonEvent
import com.radziejewskig.presentation.base.CommonState
import com.radziejewskig.presentation.mediator.NEWS_PAGE_SIZE
import com.radziejewskig.presentation.viewmodels.news.list.NewsListViewModel
import com.radziejewskig.sharedui.base.ui.BaseFragment
import com.radziejewskig.sharedui.extension.*
import com.radziejewskig.sharedui.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@ExperimentalPagingApi
@AndroidEntryPoint
internal class FragmentNewsList: BaseFragment<CommonState, CommonEvent>(R.layout.fragment_news_list) {

    @ExperimentalPagingApi
    override val viewModel: NewsListViewModel by viewModels()

    override val binding by viewBinding(FragmentNewsListBinding::bind)

    private var isRvAdapterProcessingData = false

    private var connectionJob: Job? = null

    private var internetConnected: Boolean = true

    private val newsAdapter = NewsAdapter { newsId ->
        if(!isRvAdapterProcessingData && canNavigate()) {
            viewModel.newsClicked(newsId)
            navigateToNewsDetails(newsId)
        }
    }.apply {
        stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observeValues()
        startConnectionCheckJob()
    }

    private fun setupViews() {
        binding.run {
            with(newsListRv) {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

                if (itemDecorationCount > 0) {
                    removeItemDecorationAt(0)
                }
                ContextCompat.getDrawable(requireContext(), R.drawable.divider_shape_16)?.let {
                    val decorator = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL).apply {
                        setDrawable(it)
                    }
                    addItemDecoration(decorator)
                }
                adapter = newsAdapter
                setHasFixedSize(true)
            }
            newsListSwipeToRefresh.setProgressViewOffset(false, 0, resources.getDimensionPixelSize(R.dimen.dp48))
            newsListSwipeToRefresh.setOnRefreshListener {
                newsAdapter.refresh()
            }
        }
    }

    private fun observeValues() {
        viewModel.news.collectLatestWhenStartedAutoCancelling(viewLifecycleOwner) { pagingData ->
            newsAdapter.submitData(pagingData)
        }
        newsAdapter.loadStateFlow.collectLatestWhenStartedAutoCancelling(viewLifecycleOwner) { loadStates ->
            val isRefreshing = loadStates.refresh is LoadState.Loading
            val loadingMore =
                !isRefreshing &&
                loadStates.append is LoadState.Loading &&
                !loadStates.append.endOfPaginationReached &&
                newsAdapter.itemCount >= NEWS_PAGE_SIZE

            isRvAdapterProcessingData = isRefreshing

            binding.run {
                newsListProgressBar.isVisible = isRefreshing && !newsListSwipeToRefresh.isRefreshing
                newsListRv.isVisible = !isRefreshing
                newsListMoreProgressBar.isVisible = loadingMore

                if(!isRefreshing) {
                    newsListSwipeToRefresh.isRefreshing = false
                }

                val thereWasAnError =
                    loadStates.prepend is LoadState.Error ||
                    loadStates.append is LoadState.Error ||
                    loadStates.refresh is LoadState.Error

                newsListNoConnectionLl.isVisible = (thereWasAnError && !requireContext().hasInternetConnection()) && !isRefreshing
            }
        }
    }

    private fun navigateToNewsDetails(newsId: String) {
        //TODO update the Navigation Component version when 2.4.0 becomes stable
        //
        // I cannot use safeArgs due to bug in the Navigation library version 2.4.0-alpha01
        // Fixed in navigation 2.4.0-alpha02: https://developer.android.com/jetpack/androidx/releases/navigation#2.4.0-alpha02
        navigateSafe(
            R.id.action_fragmentNewsList_to_fragmentNewsDetails,
            bundleOf("newsId" to newsId)
        )
    }

    override fun windowInsetsChanged(navigationBarHeight: Int, statusBarHeight: Int) {
        binding.run {
            newsListRv.updatePadding(
                bottom = navigationBarHeight + resources.getDimensionPixelSize(R.dimen.bottom_app_bar_height)
            )
            newsListSwipeToRefresh.updatePadding(
                top = statusBarHeight
            )
            newsListMoreProgressBar.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                bottomMargin = navigationBarHeight + resources.getDimensionPixelSize(R.dimen.bottom_app_bar_height)
            }
            newsListNoConnectionLl.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                bottomMargin = navigationBarHeight +
                         resources.getDimensionPixelSize(R.dimen.bottom_app_bar_height) +
                         resources.getDimensionPixelSize(R.dimen.dp24)
            }
        }
    }

    private fun onConnectionStatusUpdated(isConnected: Boolean) {
        binding.run {
            val wasConnected = internetConnected
            internetConnected = isConnected
            if(!wasConnected && isConnected) {
                newsListNoConnectionLl.isVisible = false
                newsAdapter.retry()
            }
        }
    }

    private fun startConnectionCheckJob() {
        connectionJob?.cancel()
        connectionJob = launchLifecycleScopeWhenStarted {
            while (isActive) {
                delay(1000)
                onConnectionStatusUpdated(requireContext().hasInternetConnection())
            }
        }
    }

    override fun onDestroyView() {
        connectionJob?.cancel()
        connectionJob = null
        super.onDestroyView()
    }

}
