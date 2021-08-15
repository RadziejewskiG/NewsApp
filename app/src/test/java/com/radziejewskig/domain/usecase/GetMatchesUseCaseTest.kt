package com.radziejewskig.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.radziejewskig.TestCoroutineRule
import com.radziejewskig.domain.model.MatchSchemaDomainModel
import com.radziejewskig.domain.repository.MatchesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class GetMatchesUseCaseTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var getMatchesUseCase: GetMatchesUseCase

    @Mock
    private lateinit var matchesRepository: MatchesRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getMatchesUseCase = GetMatchesUseCase(
            matchesRepository
        )
    }

    @Test
    fun `getMatchesUseCase calls repository and returns proper data`() = testCoroutineRule.runBlockingTest {
        val data = listOf(
            MatchSchemaDomainModel(
                id = 7,
                teamAName = "testNameA",
                scoreA = 3,
                teamBName = "testNameB",
                scoreB = 9,
            )
        )

        `when`(matchesRepository.getMatches())
            .thenReturn(data)

        val response = getMatchesUseCase()

        verify(matchesRepository).getMatches()

        assertThat(response).isEqualTo(data)
    }

}