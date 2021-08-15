package com.radziejewskig.presentation.viewmodels.matches

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import com.radziejewskig.TestCoroutineRule
import com.radziejewskig.callPrivateSuspend
import com.radziejewskig.domain.model.MatchSchemaDomainModel
import com.radziejewskig.domain.usecase.GetMatchesUseCase
import com.radziejewskig.getPrivateProperty
import com.radziejewskig.presentation.mapper.MatchSchemaDomainToPresentationMapper
import com.radziejewskig.presentation.model.MatchSchemaPresentationModel
import com.radziejewskig.presentation.model.MatchSocketUpdatePresentationModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class MatchesViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var matchesViewModel: MatchesViewModel

    private lateinit var savedStateHandle: SavedStateHandle

    @Mock
    private lateinit var getMatchesUseCase: GetMatchesUseCase

    @Mock
    private lateinit var matchSchemaDomainToPresentationMapper: MatchSchemaDomainToPresentationMapper

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        savedStateHandle = SavedStateHandle()

        matchesViewModel = MatchesViewModel(
            savedStateHandle,
            getMatchesUseCase,
            matchSchemaDomainToPresentationMapper
        )
    }

    @Test
    fun `state isLoadingMatches is false when loadMatches finishes successfully`() = testCoroutineRule.runBlockingTest {
        val matchSchemaDomainModel = MatchSchemaDomainModel(
            id = 13,
            teamAName = "testNameA",
            scoreA = 0,
            teamBName = "testNameB",
            scoreB = 0,
        )
        val data = listOf(
            matchSchemaDomainModel
        )

        `when`(getMatchesUseCase.invoke()).thenReturn(data)

        `when`(matchSchemaDomainToPresentationMapper.toPresentation(matchSchemaDomainModel))
            .thenReturn(
                MatchSchemaPresentationModel(
                    id = 13,
                    teamAName = "testNameA",
                    scoreA = 0,
                    teamBName = "testNameB",
                    scoreB = 0,
                )
            )

        callPrivateSuspend(matchesViewModel, "tryLoadMatches")

        verify(getMatchesUseCase).invoke()
        verify(matchSchemaDomainToPresentationMapper).toPresentation(matchSchemaDomainModel)

        assertThat(matchesViewModel.state.value.isLoadingMatches).isEqualTo(false)
    }

    @Test
    fun `match list has proper values when loadMatches finishes successfully`() = testCoroutineRule.runBlockingTest {
        val teamId = 13
        val teamAName = "testNameA"
        val teamBName = "testNameB"
        val scoreA = 1
        val scoreB = 3

        val domainModel = MatchSchemaDomainModel(
            id = teamId,
            teamAName = teamAName,
            scoreA = scoreA,
            teamBName = teamBName,
            scoreB = scoreB,
        )
        val data = listOf(
            domainModel
        )

        val presentationModel = MatchSchemaPresentationModel(
            id = teamId,
            teamAName = teamAName,
            scoreA = scoreA,
            teamBName = teamBName,
            scoreB = scoreB,
        )
        val expectedMatchData = listOf(presentationModel)

        val matches = matchesViewModel.getPrivateProperty<MatchesViewModel, MutableStateFlow<List<MatchSchemaPresentationModel>>>("_matches")

        matches?.let {
            it.value = emptyList()
        }

        `when`(getMatchesUseCase.invoke()).thenReturn(data)

        `when`(matchSchemaDomainToPresentationMapper.toPresentation(domainModel))
            .thenReturn(presentationModel)

        callPrivateSuspend(matchesViewModel, "tryLoadMatches")

        verify(getMatchesUseCase).invoke()
        verify(matchSchemaDomainToPresentationMapper).toPresentation(domainModel)

        assertThat(matchesViewModel.matches.value).isEqualTo(expectedMatchData)
    }

    @Test
    fun `matchSocketUpdate with correct team id updates the match list properly`() = testCoroutineRule.runBlockingTest {
        val teamId = 13
        val teamAName = "testNameA"
        val teamBName = "testNameB"

        val startData = listOf(
            MatchSchemaPresentationModel(
                id = teamId,
                teamAName = teamAName,
                scoreA = 1,
                teamBName = teamBName,
                scoreB = 3,
            )
        )

        val update = MatchSocketUpdatePresentationModel(
            id = teamId,
            scoreA = 2,
            scoreB = 3
        )

        val expectedData = listOf(
            MatchSchemaPresentationModel(
                id = teamId,
                teamAName = teamAName,
                scoreA = 2,
                teamBName = teamBName,
                scoreB = 3,
            )
        )

        val matches = matchesViewModel.getPrivateProperty<MatchesViewModel, MutableStateFlow<List<MatchSchemaPresentationModel>>>("_matches")

        matches?.let {
            it.value = startData
        }

        matchesViewModel.matchesUpdated(update)

        assertThat(matchesViewModel.matches.value).isEqualTo(expectedData)
    }

    @Test
    fun `matchSocketUpdate with wrong team id doesn't update the match list`() = testCoroutineRule.runBlockingTest {
        val teamAName = "testNameA"
        val teamBName = "testNameB"

        val startData = listOf(
            MatchSchemaPresentationModel(
                id = 13,
                teamAName = teamAName,
                scoreA = 1,
                teamBName = teamBName,
                scoreB = 3,
            ),
            MatchSchemaPresentationModel(
                id = 5,
                teamAName = teamAName,
                scoreA = 2,
                teamBName = teamBName,
                scoreB = 3,
            )
        )

        val update = MatchSocketUpdatePresentationModel(
            id = 1,
            scoreA = 2,
            scoreB = 3
        )

        val matches = matchesViewModel.getPrivateProperty<MatchesViewModel, MutableStateFlow<List<MatchSchemaPresentationModel>>>("_matches")

        matches?.let {
            it.value = startData
        }

        matchesViewModel.matchesUpdated(update)

        assertThat(matchesViewModel.matches.value).isEqualTo(startData)
    }

}
