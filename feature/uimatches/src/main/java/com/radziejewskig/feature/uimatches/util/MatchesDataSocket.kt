package com.radziejewskig.feature.uimatches.util

import android.app.Activity
import android.content.Context
import com.footballco.recruitment.MatchesSocketFake
import com.radziejewskig.feature.uimatches.mapper.MatchSocketUpdateToPresentationMapper
import com.radziejewskig.presentation.model.MatchSocketUpdatePresentationModel
import com.radziejewskig.feature.uimatches.util.MatchesDataSocketStatus.NOT_INITIALIZED
import com.radziejewskig.feature.uimatches.util.MatchesDataSocketStatus.STARTED
import com.radziejewskig.feature.uimatches.util.MatchesDataSocketStatus.STOPPED
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@ActivityScoped
class MatchesDataSocket @Inject constructor(
    @ActivityContext private val activityContext: Context,
    private val matchSocketUpdateToPresentationMapper: MatchSocketUpdateToPresentationMapper
) {

    private val activity = activityContext as Activity

    private val matchesSocketFake by lazy { MatchesSocketFake(activity) }

    private var updateJob: Job? = null

    private var state: MatchesDataSocketStatus = NOT_INITIALIZED

    fun listenToUpdates(
        onMatchUpdated: (MatchSocketUpdatePresentationModel) -> Unit
    ) {
        if(state == NOT_INITIALIZED) {
            state = STARTED
            updateJob?.cancel()
            updateJob = CoroutineScope(Dispatchers.Main).launch {
                matchesSocketFake.start()
                matchesSocketFake.onMatchUpdate = {
                    onMatchUpdated(
                        matchSocketUpdateToPresentationMapper.toPresentation(it)
                    )
                }
            }
        }
    }

    fun startUpdates() {
        if(state == STOPPED) {
            state = STARTED
            matchesSocketFake.start()
        }
    }

    fun stopUpdates() {
        if(state == STARTED) {
            state = STOPPED
            matchesSocketFake.stop()
        }
    }

    fun clearResources() {
        state = NOT_INITIALIZED
        matchesSocketFake.stop()
        updateJob?.cancel()
        updateJob = null
    }

}

private enum class MatchesDataSocketStatus {
    NOT_INITIALIZED,
    STARTED,
    STOPPED
}
