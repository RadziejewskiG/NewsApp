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
class GetNewsPagingSourceUseCaseTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var getNewsPagingSourceUseCase: GetNewsPagingSourceUseCase

    @Mock
    private lateinit var newsRepository: NewsRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getNewsPagingSourceUseCase = GetNewsPagingSourceUseCase(
            newsRepository
        )
    }

    @Test
    fun `getNewsPagingSourceUseCase calls repository and returns proper data`() = testCoroutineRule.runBlockingTest {

        // I can't create an instance of PagingSource so I've used the fact that
        // the return value of getNewsPagingSource in the repository interface is the type
        // of Any because the domain layer must not know about the Paging library
        val data = "I should not pass Any, so I've just created a String"

        `when`(newsRepository.getNewsPagingSource())
            .thenReturn(data)

        val response = getNewsPagingSourceUseCase()

        verify(newsRepository).getNewsPagingSource()

        assertThat(response).isEqualTo(data)
    }

}
