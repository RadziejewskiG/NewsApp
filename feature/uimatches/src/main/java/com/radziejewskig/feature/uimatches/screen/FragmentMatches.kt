package com.radziejewskig.feature.uimatches.screen

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.radziejewskig.feature.uimatches.R
import com.radziejewskig.feature.uimatches.databinding.FragmentMatchesBinding
import com.radziejewskig.feature.uimatches.util.MatchesDataSocket
import com.radziejewskig.presentation.viewmodels.matches.MatchesEvent
import com.radziejewskig.presentation.viewmodels.matches.MatchesState
import com.radziejewskig.presentation.viewmodels.matches.MatchesViewModel
import com.radziejewskig.sharedui.base.ui.BaseFragment
import com.radziejewskig.sharedui.extension.collectLatestWhenStarted
import com.radziejewskig.sharedui.extension.viewLifecycleScope
import com.radziejewskig.sharedui.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class FragmentMatches: BaseFragment<MatchesState, MatchesEvent>(R.layout.fragment_matches) {

    override val viewModel: MatchesViewModel by viewModels()

    override val binding by viewBinding(FragmentMatchesBinding::bind)

    private val matchesAdapter: MatchesAdapter by lazy { MatchesAdapter() }

    @Inject
    lateinit var matchesDataSocket: MatchesDataSocket

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            matchesRv.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                if (itemDecorationCount > 0) {
                    removeItemDecorationAt(0)
                }
                ContextCompat.getDrawable(requireContext(), R.drawable.divider_shape_1_5)?.let {
                    val decorator = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL).apply {
                        setDrawable(it)
                    }
                    addItemDecoration(decorator)
                }
                adapter = matchesAdapter
                setHasFixedSize(true)
            }
        }

        // Initialize the MatchesDataSocket in case of config changes which ViewModel survives(matches list is already populated)
        if(viewModel.areMatchesLoaded()) {
            setupMatchUpdates()
        }
        observeValues()
    }

    private fun observeValues() {
        viewModel.matches.collectLatestWhenStarted(viewLifecycleScope) { matches ->
            matchesAdapter.submitList(matches)
        }
    }

    override fun handleState(state: MatchesState) {
        binding.matchesProgressBar.isVisible = state.isLoadingMatches
    }

    override fun handleSingleEvent(event: MatchesEvent?) {
        when(event) {
            is MatchesEvent.ListLoaded -> setupMatchUpdates()
        }
    }

    private fun setupMatchUpdates() {
        matchesDataSocket.listenToUpdates(viewModel::matchesUpdated)
    }

    override fun windowInsetsChanged(navigationBarHeight: Int, statusBarHeight: Int) {
        binding.matchesRv.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            topMargin = statusBarHeight
        }
        binding.matchesRv.updatePadding(
            bottom = navigationBarHeight + resources.getDimensionPixelSize(R.dimen.bottom_app_bar_height)
        )
    }

    override fun onStop() {
        super.onStop()
        matchesDataSocket.stopUpdates()
    }

    override fun onStart() {
        super.onStart()
        matchesDataSocket.startUpdates()
    }

    override fun onDestroyView() {
        matchesDataSocket.clearResources()
        super.onDestroyView()
    }

}
