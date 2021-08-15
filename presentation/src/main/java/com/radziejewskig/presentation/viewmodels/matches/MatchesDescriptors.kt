package com.radziejewskig.presentation.viewmodels.matches

import com.radziejewskig.presentation.base.CommonEvent
import com.radziejewskig.presentation.base.CommonState
import kotlinx.parcelize.Parcelize

@Parcelize
data class MatchesState(
    val isLoadingMatches: Boolean
): CommonState()

sealed class MatchesEvent: CommonEvent {
    object ListLoaded: MatchesEvent()
}
