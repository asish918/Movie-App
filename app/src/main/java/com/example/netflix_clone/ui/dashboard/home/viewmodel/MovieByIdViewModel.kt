package com.example.netflix_clone.ui.dashboard.home.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.netflix_clone.domain.model.Movie
import com.example.netflix_clone.domain.repo.MoviesRepository
import com.example.netflix_clone.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieByIdViewModel @Inject constructor(
  private val repository: MoviesRepository
) : ViewModel() {

  var movie by mutableStateOf<Resource<Movie>>(Resource.Loading)
    private set

  init {
    fetchMovieById(movieId = 520763)
  }

  private fun fetchMovieById(movieId: Long) {
    viewModelScope.launch {
      movie = Resource.Loading
      repository.getMovieById(movieId = movieId)
        .subscribe(
          { error ->
            movie = Resource.Error(error)
          },
          { data ->
            movie = Resource.Success(data)
          }
        )
    }
  }
}