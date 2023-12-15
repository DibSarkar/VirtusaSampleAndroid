package com.example.android.presentation.movie_details

import androidx.lifecycle.ViewModel
import com.example.android.common.Resource
import com.example.android.domain.model.MovieDetails
import com.example.android.domain.use_case.get_movie_details.GetMovieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : ViewModel(), CoroutineScope {

    private val _movieDetails = MutableStateFlow<Resource<MovieDetails>>(Resource.Loading())
    val movieDetails = _movieDetails.asStateFlow()

     fun getMovieDetails(movieId: Int) = launch(coroutineContext) { getMovieDetailsUseCase(movieId).collect {_movieDetails.value = it } }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO
}