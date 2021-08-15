package com.radziejewskig.feature.uinews.screen.newsdetails

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.URLUtil
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import com.radziejewskig.feature.uinews.R
import com.radziejewskig.feature.uinews.databinding.FragmentNewsDetailsBinding
import com.radziejewskig.presentation.base.CommonEvent
import com.radziejewskig.presentation.viewmodels.news.details.NewsDetailsState
import com.radziejewskig.presentation.viewmodels.news.details.NewsDetailsViewModel
import com.radziejewskig.sharedui.base.ui.BaseFragment
import com.radziejewskig.sharedui.extension.loadImage
import com.radziejewskig.sharedui.extension.toDateText
import com.radziejewskig.sharedui.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class FragmentNewsDetails: BaseFragment<NewsDetailsState, CommonEvent>(R.layout.fragment_news_details) {

    override val viewModel: NewsDetailsViewModel by viewModels()

    override val binding by viewBinding(FragmentNewsDetailsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        binding.newsDetailsBody.apply {
            webViewClient = object: WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    url?.let { openUrlInBrowser(it) }
                    return true
                }
            }
            settings.apply {
                javaScriptEnabled = true
                builtInZoomControls = false
            }
        }
    }

    private fun openUrlInBrowser(url: String) {
        if(url.isNotEmpty() && URLUtil.isValidUrl(url)) {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
        }
    }

    override fun handleState(state: NewsDetailsState) {
        binding.run {
            if(state.isLoadingNews){
                newsDetailsShimmerFr.isVisible = true
                newsDetailsContent.isVisible = false
            } else {
                state.news?.let {
                    newsDetailsContent.isVisible = true
                    newsDetailsTitle.text = it.headline
                    newsDetailsDate.text = it.publishedTime.toDateText(requireContext())
                    newsDetailsBody.loadData(it.body, "text/html; charset=utf-8", "UTF-8")
                    newsDetailsImage.loadImage(
                        requireContext(),
                        it.urlImage,
                        placeholderDrawableRes = null,
                        animate = true
                    ) {
                        newsDetailsShimmerFr.isVisible = false
                    }
                }
            }
        }
    }

    override fun getArgsData() {
        //TODO update the Navigation Component version when 2.4.0 becomes stable
        //
        // I cannot use safeArgs due to bug in the Navigation library version 2.4.0-alpha01
        // Fixed in navigation 2.4.0-alpha02: https://developer.android.com/jetpack/androidx/releases/navigation#2.4.0-alpha02
        requireArguments().let { args ->
            args.getString("newsId", null)?.let {
                viewModel.setup(it)
            }
        }
    }

    override fun windowInsetsChanged(navigationBarHeight: Int, statusBarHeight: Int) {
        binding.run {
            newsDetailsContainer.updatePadding(
                top = statusBarHeight,
                bottom = navigationBarHeight + resources.getDimensionPixelSize(R.dimen.bottom_app_bar_height)
            )
        }
    }

}
