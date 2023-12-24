package com.example.netflix_clone.util

sealed class Resource<out T> {
  data class Success<out T>(val data: T) : Resource<T>()
  data class Error(val error: com.example.netflix_clone.util.Error) : Resource<Nothing>()
  object Loading : Resource<Nothing>()
}