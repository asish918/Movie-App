package com.example.netflix_clone.di

import com.example.netflix_clone.data.network.client.MoviesApiClient
import com.example.netflix_clone.data.network.service.MoviesService
import com.example.netflix_clone.domain.repo.MoviesRepository
import com.example.netflix_clone.domain.repo.MoviesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

  @Provides
  fun provideHttpClient(): OkHttpClient = MoviesApiClient.createHttpClient()

  @Provides
  @Singleton
  fun provideMoviesService(client: OkHttpClient): MoviesService =
    MoviesApiClient.createMoviesService(client)

  @Provides
  @Singleton
  fun provideMoviesRepository(service: MoviesService): MoviesRepository =
    MoviesRepositoryImpl(service)
}