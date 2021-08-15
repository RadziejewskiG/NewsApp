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
class DeleteAllNewsUseCaseTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var deleteAllNewsUseCase: DeleteAllNewsUseCase

    @Mock
    private lateinit var newsRepository: NewsRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        deleteAllNewsUseCase = DeleteAllNewsUseCase(
            newsRepository
        )
    }

    @Test
    fun `deleteAllNewsUseCase calls repository and returns proper boolean`() = testCoroutineRule.runBlockingTest {
        val valueToReturn = true

        `when`(newsRepository.deleteAllNews())
            .thenReturn(valueToReturn)

        val response = deleteAllNewsUseCase()

        verify(newsRepository).deleteAllNews()

        assertThat(response).isEqualTo(valueToReturn)
    }

}