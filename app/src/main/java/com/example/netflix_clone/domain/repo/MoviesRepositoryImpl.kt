package com.example.netflix_clone.domain.repo

import com.example.netflix_clone.domain.model.Movie
import com.example.netflix_clone.domain.model.MovieVideo
//import com.example.netflix_clone.BuildConfig
import com.example.netflix_clone.data.network.service.MoviesService
import com.example.netflix_clone.util.Error
import com.example.netflix_clone.util.Error.UnexpectedError
import com.example.netflix_clone.util.Single
import com.example.netflix_clone.util.success
import timber.log.Timber

class MoviesRepositoryImpl(
  private val service: MoviesService
) : MoviesRepository {

  companion object {
    private const val TAG = "MoviesRepo"
  }

  // Use for Netflix Originals
  override suspend fun getTopRatedMovies(
    language: String,
    page: Int
  ): Single<Error, List<Movie>> {
    return try {
      val result = service.getTopRatedMovies(
        language = language,
        page = page,
        apiKey = "8ae408fce8fcaf813f449330a5003f09"
      ).results.map { it.asDomainModel() }
      success(result)
    } catch (e: Exception) {
      Timber.tag(TAG).e("Exception: ${e.message}")
      error(UnexpectedError)
    }
  }

  // Use for Trending Now
  override suspend fun getNowPlayingMovies(
    language: String,
    page: Int
  ): Single<Error, List<Movie>> {
    return try {
      val result = service.getNowPlayingMovies(
        language = language,
        page = page,
        apiKey = "8ae408fce8fcaf813f449330a5003f09"
      ).results.map { it.asDomainModel() }
      success(result)
    } catch (e: Exception) {
      Timber.tag(TAG).e("Exception: ${e.message}")
      error(UnexpectedError)
    }
  }

  // Use for Popular On JetFlix
  override suspend fun getPopularMovies(
    language: String,
    page: Int
  ): Single<Error, List<Movie>> {
    return try {
      val result = service.getPopularMovies(
        language = language,
        page = page,
        apiKey = "8ae408fce8fcaf813f449330a5003f09"
      ).results.map { it.asDomainModel() }
      success(result)
    } catch (e: Exception) {
      Timber.tag(TAG).e("Exception: ${e.message}")
      error(UnexpectedError)
    }
  }

  override suspend fun getMovieById(
    movieId: Long
  ): Single<Error, Movie> {
    return try {
      val result = service.getMovieById(
        movieId = movieId,
        apiKey = "8ae408fce8fcaf813f449330a5003f09"
      ).asDomainModel()
      success(result)
    } catch (e: Exception) {
      Timber.tag(TAG).e("Exception: ${e.message}")
      error(UnexpectedError)
    }
  }

  override suspend fun getMovieVideosById(
    movieId: Long
  ): Single<Error, MovieVideo> {
    return try {
      val result = service.getMovieVideosById(
        movieId = movieId,
        apiKey = "8ae408fce8fcaf813f449330a5003f09"
      ).results.first().asDomainModel()
      success(result)
    } catch (e: Exception) {
      Timber.tag(TAG).e("Exception: ${e.message}")
      error(UnexpectedError)
    }
  }

  override suspend fun getSimilarMovies(
    movieId: Long
  ): Single<Error, List<Movie>> {
    return try {
      val result = service.getSimilarMovies(
        movieId = movieId,
        apiKey = "8ae408fce8fcaf813f449330a5003f09"
      ).results.map { it.asDomainModel() }
      success(result)
    } catch (e: Exception) {
      Timber.tag(TAG).e("Exception: ${e.message}")
      error(UnexpectedError)
    }
  }
}