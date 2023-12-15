package com.example.android.domain.repository

import com.example.android.common.Resource
import com.example.android.domain.model.Movie
import com.example.android.domain.model.MovieDetails
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getPopularMovies(): Flow<Resource<List<Movie>>>

    suspend fun getMovieDetails(movieId: Int): Flow<Resource<MovieDetails>>
}