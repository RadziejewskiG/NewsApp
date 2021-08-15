package com.radziejewskig.presentation.viewmodels.matches

import androidx.lifecycle.SavedStateHandle
import com.radziejewskig.domain.usecase.GetMatchesUseCase
import com.radziejewskig.presentation.base.viewmodel.FragmentViewModel
import com.radziejewskig.presentation.mapper.MatchSchemaDomainToPresentationMapper
import com.radziejewskig.presentation.model.MatchSchemaPresentationModel
import com.radziejewskig.presentation.model.MatchSocketUpdatePresentationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MatchesViewModel @Inject constructor (
    private val handle: SavedStateHandle,
    private val getMatchesUseCase: GetMatchesUseCase,
    private val matchSchemaDomainToPresentationMapper: MatchSchemaDomainToPresentationMapper
): FragmentViewModel<MatchesState, MatchesEvent>(handle) {

    override fun setupInitialState() = MatchesState(isLoadingMatches = true)

    private val _matches = MutableStateFlow<List<MatchSchemaPresentationModel>>(emptyList())
    var matches = _matches.asStateFlow()

    fun areMatchesLoaded(): Boolean = _matches.value.isNotEmpty()

    override fun initialAct() {
        loadMatches()
    }

    private fun loadMatches() {
        launch (
            content = {
              tryLoadMatches()
            },
            onError = {
                mutateState {
                    copy(
                        isLoadingMatches = false
                    )
                }
            }
        )
    }

    private suspend fun tryLoadMatches() {
        mutateState {
            copy(isLoadingMatches = true)
        }
        val response = getMatchesUseCase()
        _matches.update {
            response.map { matchSchemaDomainToPresentationMapper.toPresentation(it) }
        }
        mutateState {
            copy(isLoadingMatches = false)
        }
        MatchesEvent.ListLoaded.emit()
    }

    fun matchesUpdated(matchUpdate: MatchSocketUpdatePresentationModel) {
        val index = _matches.value.indexOfFirst { it.id == matchUpdate.id }
        if(index >= 0) {
            val newItem = _matches.value[index].copy(
                scoreA = matchUpdate.scoreA,
                scoreB = matchUpdate.scoreB,
            )
            _matches.update { matchesList ->
                matchesList.toMutableList().apply {
                    this[index] = newItem
                }
            }
        }
    }

}
