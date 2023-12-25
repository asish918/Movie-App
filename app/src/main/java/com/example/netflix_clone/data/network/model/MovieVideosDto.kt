package com.example.netflix_clone.data.network.model

import com.example.netflix_clone.domain.model.MovieVideo

data class MoviesVideosDto(
  val results: List<MovieVideoItemDto>
)

data class MovieVideoItemDto(
  val key: String,
  val name: String,
  val size: Int,
  val type: String
) {

  fun asDomainModel() = MovieVideo(
    key = key,
    url = "https://www.youtube.com/watch?v=$key",
    name = name,
    size = size,
    type = type
  )
}