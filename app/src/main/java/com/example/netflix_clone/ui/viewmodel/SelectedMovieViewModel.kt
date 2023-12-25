package com.example.netflix_clone.ui.viewmodel

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
class SelectedMovieViewModel @Inject constructor(
  val repository: MoviesRepository
) : ViewModel() {

  var selectedMovie by mutableStateOf<Resource<Movie?>>(Resource.Loading)
    private set

  var similarMovies by mutableStateOf<Resource<List<Movie?>>>(Resource.Loading)
    private set

  fun setSelectedMovie(movie: Movie?) {
    selectedMovie = Resource.Success(movie)
  }

  fun fetchSimilarMovies(movieId: Long) {
    viewModelScope.launch {
      similarMovies = Resource.Loading
      repository.getSimilarMovies(movieId)
        .subscribe(
          { error ->
            similarMovies = Resource.Error(error)
          },
          { data ->
            similarMovies = Resource.Success(data)
          }
        )
    }
  }
}