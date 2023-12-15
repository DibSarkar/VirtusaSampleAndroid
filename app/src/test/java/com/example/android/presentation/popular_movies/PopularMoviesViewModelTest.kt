package com.example.android.presentation.popular_movies

import androidx.lifecycle.Observer
import com.example.android.common.Resource
import com.example.android.common.UiText
import com.example.android.domain.model.Movie
import com.example.android.domain.use_case.get_popular_movies.GetPopularMoviesUseCase
import com.example.android.utils.TestingDispatcher
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class PopularMoviesViewModelTest {

    @MockK
    private lateinit var apiObserver: Observer<Resource<List<Movie>>>

    @MockK
    lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCase

    private lateinit var popularMoviesViewModel: PopularMoviesViewModel

    private val testDispatcher = TestingDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when use case returns success then resource should be success`() {
        runTest {
            every { apiObserver.onChanged(any()) } answers { }
            coEvery { getPopularMoviesUseCase() } returns flow { emit(Resource.Success(listOf())) }

            popularMoviesViewModel = PopularMoviesViewModel(getPopularMoviesUseCase, testDispatcher)

            coVerify (exactly = 1) { getPopularMoviesUseCase() }

            assert(popularMoviesViewModel.movies.value is Resource.Success)
            assert(popularMoviesViewModel.movies.value.data != null)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when use case returns error then resource should be error`() {
        runTest{
            every { apiObserver.onChanged(any()) } answers { }
            coEvery { getPopularMoviesUseCase() } returns flow { emit(Resource.Error(UiText.unknownError())) }

            popularMoviesViewModel = PopularMoviesViewModel(getPopularMoviesUseCase, testDispatcher)

            coVerify(exactly = 1) { getPopularMoviesUseCase() }

            assert(popularMoviesViewModel.movies.value is Resource.Error)
            assert(popularMoviesViewModel.movies.value.uiText != null)

        }
    }
}