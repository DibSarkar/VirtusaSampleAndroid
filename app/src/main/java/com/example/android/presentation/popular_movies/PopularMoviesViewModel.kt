package com.example.android.presentation.popular_movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.common.Resource
import com.example.android.core.dispatchers.Dispatcher
import com.example.android.domain.model.Movie
import com.example.android.domain.use_case.get_popular_movies.GetPopularMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase, private val appDispatcher: Dispatcher
) : ViewModel() {

    private val _movies = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading())
    val movies = _movies.asStateFlow()

    init {
        getPopularMovies()
    }

    private fun getPopularMovies() {
        launchOnViewModelScope(appDispatcher.io) {
            getPopularMoviesUseCase().collect {
                _movies.value = it
            }
        }
    }

    private fun launchOnViewModelScope(
        coroutineDispatcher: CoroutineDispatcher,
        coroutine: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch(coroutineDispatcher, block = coroutine)
}