package com.example.netflix_clone.domain.repo

import com.example.netflix_clone.domain.model.Movie
import com.example.netflix_clone.domain.model.MovieVideo
import com.example.netflix_clone.util.Single
import com.example.netflix_clone.util.Error
interface MoviesRepository {

  suspend fun getTopRatedMovies(
    language: String,
    page: Int
  ): Single<Error, List<Movie>>

  suspend fun getNowPlayingMovies(
    language: String,
    page: Int
  ): Single<Error, List<Movie>>

  suspend fun getPopularMovies(
    language: String,
    page: Int
  ): Single<Error, List<Movie>>

  suspend fun getMovieById(
    movieId: Long
  ): Single<Error, Movie>

  suspend fun getMovieVideosById(
    movieId: Long
  ): Single<Error, MovieVideo>

  suspend fun getSimilarMovies(
    movieId: Long
  ): Single<Error, List<Movie>>
}