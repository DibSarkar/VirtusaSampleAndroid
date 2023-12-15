package com.example.android.presentation.movie_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.common.Resource
import com.example.android.core.dispatchers.Dispatcher
import com.example.android.domain.model.MovieDetails
import com.example.android.domain.use_case.get_movie_details.GetMovieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase, private val appDispatcher: Dispatcher
) : ViewModel() {

    private val _movieDetails = MutableStateFlow<Resource<MovieDetails>>(Resource.Loading())
    val movieDetails = _movieDetails.asStateFlow()

    fun getMovieDetails(movieId: Int) {
        launchOnViewModelScope(appDispatcher.io) {
            getMovieDetailsUseCase(movieId).collect {
                _movieDetails.value = it
            }
        }
    }

    private fun launchOnViewModelScope(
        coroutineDispatcher: CoroutineDispatcher,
        coroutine: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch(coroutineDispatcher, block = coroutine)
}