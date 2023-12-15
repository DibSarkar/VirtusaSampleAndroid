package com.example.android.presentation.movie_details

import com.example.android.common.Resource
import com.example.android.common.UiText
import com.example.android.domain.model.MovieDetails
import com.example.android.domain.use_case.get_movie_details.GetMovieDetailsUseCase
import com.example.android.utils.TestingDispatcher
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class MovieDetailsViewModelTest {

    @MockK
    lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase

    private lateinit var movieDetailsViewModel: MovieDetailsViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        val testDispatcher = TestingDispatcher()
        movieDetailsViewModel = MovieDetailsViewModel(getMovieDetailsUseCase, testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when use case returns success then resource returned should be success`() {
        runTest {
            coEvery { getMovieDetailsUseCase(1) } returns flow { emit(Resource.Success(MovieDetails())) }

            movieDetailsViewModel.getMovieDetails(1)
            coVerify(exactly = 1) { getMovieDetailsUseCase(1) }

            assert(movieDetailsViewModel.movieDetails.value is Resource.Success)
            assert(movieDetailsViewModel.movieDetails.value.data != null)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when use case returns error then resource returned should be error`() {
        runTest {
            coEvery { getMovieDetailsUseCase(1) } returns flow { emit(Resource.Error(UiText.unknownError())) }

            movieDetailsViewModel.getMovieDetails(1)
            coVerify(exactly = 1) { getMovieDetailsUseCase(1) }

            assert(movieDetailsViewModel.movieDetails.value is Resource.Error)
            assert(movieDetailsViewModel.movieDetails.value.uiText != null)
        }
    }
}