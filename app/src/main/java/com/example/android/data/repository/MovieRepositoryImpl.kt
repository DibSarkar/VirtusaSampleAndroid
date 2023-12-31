package com.example.android.data.repository

import com.example.android.common.Resource
import com.example.android.common.UiText
import com.example.android.data.remote.MovieApi
import com.example.android.data.remote.model.toMovie
import com.example.android.data.remote.model.toMovieDetails
import com.example.android.domain.model.Movie
import com.example.android.domain.model.MovieDetails
import com.example.android.domain.repository.MovieRepository
import com.example.android.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val api: MovieApi) : MovieRepository {

    override suspend fun getPopularMovies(): Flow<Resource<List<Movie>>> = flow {
        try {
            emit(Resource.Loading())
            val list = api.getPopularMovies().movieList.map { it.toMovie() }
            emit(Resource.Success(list))
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    UiText.StringResource(R.string.error_couldnt_reach_server)
                )
            )
        } catch (e: IOException) {
            emit(Resource.Error(UiText.StringResource(R.string.oops_something_went_wrong)))
        }
    }

    override suspend fun getMovieDetails(movieId: Int): Flow<Resource<MovieDetails>> = flow {
        try {
            emit(Resource.Loading())
            val movieDetails = api.getMovieDetails(movieId).toMovieDetails()
            emit(Resource.Success(movieDetails))
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    UiText.StringResource(R.string.error_couldnt_reach_server)
                )
            )
        } catch (e: IOException) {
            emit(Resource.Error(UiText.StringResource(R.string.oops_something_went_wrong)))
        }
    }
}