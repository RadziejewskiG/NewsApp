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
class GetNewsPagedUseCaseTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var getNewsPagedUseCase: GetNewsPagedUseCase

    @Mock
    private lateinit var newsRepository: NewsRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getNewsPagedUseCase = GetNewsPagedUseCase(
            newsRepository
        )
    }

    @Test
    fun `getNewsPagedUseCase calls repository and returns proper data`() = testCoroutineRule.runBlockingTest {
        val page = 1

        val data = listOf(
            NewsDomainModel (
                id = "sdfsd34f4534gtd",
                isRead = true,
                publishedTime = 45367455334,
                headline = "headline",
                body = "body",
                urlImage = ""
            ),
            NewsDomainModel (
                id = "sdfsd3gf5r666td",
                isRead = false,
                publishedTime = 6464563643,
                headline = "headline2",
                body = "body2",
                urlImage = "vdgfdf.jpeg"
            )
        )

        `when`(newsRepository.getNewsPaged(page))
            .thenReturn(data)

        val response = getNewsPagedUseCase(page)

        verify(newsRepository).getNewsPaged(page)

        assertThat(response).isEqualTo(data)
    }

}
