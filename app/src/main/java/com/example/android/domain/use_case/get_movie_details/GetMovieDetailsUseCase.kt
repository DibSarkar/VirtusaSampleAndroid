package com.example.android.domain.use_case.get_movie_details

import com.example.android.common.Resource
import com.example.android.domain.model.MovieDetails
import com.example.android.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): Flow<Resource<MovieDetails>> =
        repository.getMovieDetails(movieId)
}