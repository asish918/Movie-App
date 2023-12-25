package com.example.netflix_clone.ui.dashboard.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.netflix_clone.domain.model.Movie
import com.example.netflix_clone.ui.components.LargeMovieItem
import com.example.netflix_clone.ui.theme.NetflixTheme

@Composable
fun NetflixOriginals(
  onMovieClick: (Long) -> Unit,
  modifier: Modifier = Modifier,
  netflixOriginalMovies: List<Movie>
) {
  Column(modifier = modifier) {
    Text(
      text = "Netflix Originals",
      style = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        letterSpacing = 0.25.sp
      ),
      color = NetflixTheme.colors.textPrimary,
      modifier = Modifier.padding(start = 8.dp)
    )
    Spacer(modifier = Modifier.height(12.dp))
    TrendingNowMoviesCarousel(movies = netflixOriginalMovies, onMovieSelected = onMovieClick)
  }
}

@Composable
private fun TrendingNowMoviesCarousel(
  movies: List<Movie>,
  onMovieSelected: (Long) -> Unit
) {
  LazyRow(modifier = Modifier.padding(start = 8.dp)) {
    items(movies) { movie ->
      LargeMovieItem(movie, onMovieSelected = onMovieSelected)
      Spacer(modifier = Modifier.width(8.dp))
    }
  }
}