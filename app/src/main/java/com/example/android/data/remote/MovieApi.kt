package com.example.android.data.remote

import com.example.android.data.remote.model.MovieDetailsDto
import com.example.android.data.remote.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {

    @GET("movie/popular")
    suspend fun getPopularMovies(): MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId: Int): MovieDetailsDto
}