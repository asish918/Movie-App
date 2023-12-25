package com.example.netflix_clone.ui.dashboard.home.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.netflix_clone.domain.model.Movie
import com.example.netflix_clone.domain.repo.MoviesRepository
import com.example.netflix_clone.util.Resource
import com.example.netflix_clone.data.network.constant.MoviesApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopRatedMoviesViewModel @Inject constructor(
  private val repository: MoviesRepository
) : ViewModel() {

  var topRatedMovies by mutableStateOf<Resource<List<Movie>>>(Resource.Loading)
    private set

  init {
    fetchTopRatedMovies()
  }

  fun getTopRatedMovies(): List<Movie>? {
    return when (topRatedMovies) {
      is Resource.Error -> null
      Resource.Loading -> null
      is Resource.Success -> (topRatedMovies as Resource.Success<List<Movie>>).data
    }
  }

  fun getTopRatedMovieDetails(id: Long): Movie? {
    return when (topRatedMovies) {
      is Resource.Error -> null
      Resource.Loading -> null
      is Resource.Success -> (topRatedMovies as Resource.Success<List<Movie>>).data.find { it.id == id }
    }
  }

  private fun fetchTopRatedMovies() {
    viewModelScope.launch {
      topRatedMovies = Resource.Loading
      repository.getTopRatedMovies(language = MoviesApi.LANG_ENG, page = (0..5).random())
        .subscribe(
          { error ->
            topRatedMovies = Resource.Error(error)
          },
          { data ->
            topRatedMovies = Resource.Success(data)
          }
        )
    }
  }
}