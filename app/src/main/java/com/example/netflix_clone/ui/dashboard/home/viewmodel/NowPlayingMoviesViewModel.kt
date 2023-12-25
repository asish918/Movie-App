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
class NowPlayingMoviesViewModel @Inject constructor(
  private val repository: MoviesRepository
) : ViewModel() {

  var nowPlayingMovies by mutableStateOf<Resource<List<Movie>>>(Resource.Loading)
    private set

  init {
    fetchNowPlayingMovies()
  }

  fun getTopRatedMovies(): List<Movie>? {
    return when (nowPlayingMovies) {
      is Resource.Error -> null
      Resource.Loading -> null
      is Resource.Success -> (nowPlayingMovies as Resource.Success<List<Movie>>).data
    }
  }

  fun getTopRatedMovieDetails(id: Long): Movie? {
    return when (nowPlayingMovies) {
      is Resource.Error -> null
      Resource.Loading -> null
      is Resource.Success -> (nowPlayingMovies as Resource.Success<List<Movie>>).data.find { it.id == id }
    }
  }

  private fun fetchNowPlayingMovies() {
    viewModelScope.launch {
      nowPlayingMovies = Resource.Loading
      repository.getNowPlayingMovies(language = MoviesApi.LANG_ENG, page = (0..5).random())
        .subscribe(
          { error ->
            nowPlayingMovies = Resource.Error(error)
          },
          { data ->
            nowPlayingMovies = Resource.Success(data)
          }
        )
    }
  }
}