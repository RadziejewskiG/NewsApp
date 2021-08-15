package com.radziejewskig.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.radziejewskig.TestCoroutineRule
import com.radziejewskig.domain.repository.NewsRepository
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
class UpdateNewsReadStatusByNewsIdUseCaseTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var updateNewsReadStatusByNewsIdUseCase: UpdateNewsReadStatusByNewsIdUseCase

    @Mock
    private lateinit var newsRepository: NewsRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        updateNewsReadStatusByNewsIdUseCase = UpdateNewsReadStatusByNewsIdUseCase(
            newsRepository
        )
    }

    @Test
    fun `updateNewsReadStatusByNewsIdUseCase calls repository and returns proper boolean value`() = testCoroutineRule.runBlockingTest {
        val returnValue = true

        val newsId = "fdsf4gtdg4e3"

        `when`(newsRepository.updateNewsReadStatusByNewsId(newsId))
            .thenReturn(returnValue)

        val response = updateNewsReadStatusByNewsIdUseCase(newsId)

        verify(newsRepository).updateNewsReadStatusByNewsId(newsId)

        assertThat(response).isEqualTo(returnValue)
    }

}
