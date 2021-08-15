package com.radziejewskig.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.radziejewskig.TestCoroutineRule
import com.radziejewskig.domain.model.NewsDomainModel
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
class GetNewsDetailsUseCaseTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var getNewsDetailsUseCase: GetNewsDetailsUseCase

    @Mock
    private lateinit var newsRepository: NewsRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getNewsDetailsUseCase = GetNewsDetailsUseCase(
            newsRepository
        )
    }

    @Test
    fun `getNewsDetailsUseCase calls repository and returns proper data`() = testCoroutineRule.runBlockingTest {
        val newsId = "fd434d"

        val data = NewsDomainModel (
            id = newsId,
            isRead = true,
            publishedTime = 45367455334,
            headline = "headline",
            body = "body",
            urlImage = ""
        )

        `when`(newsRepository.getNewsDetails(newsId))
            .thenReturn(data)

        val response = getNewsDetailsUseCase(newsId)

        verify(newsRepository).getNewsDetails(newsId)

        assertThat(response).isEqualTo(data)
    }

}
