package com.example.netflix_clone.ui.dashboard.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.netflix_clone.ui.viewmodel.ViewModelProvider
import com.example.netflix_clone.domain.repo.movies
import com.example.netflix_clone.ui.components.HighlightMovieItem
import com.example.netflix_clone.domain.model.Movie
import com.example.netflix_clone.ui.components.Card
import com.example.netflix_clone.ui.components.NetflixSurface
import com.example.netflix_clone.ui.dashboard.home.component.InfoButton
import com.example.netflix_clone.ui.dashboard.home.component.MyListButton
import com.example.netflix_clone.ui.dashboard.home.component.NetflixOriginals
import com.example.netflix_clone.ui.dashboard.home.component.PlayButton
import com.example.netflix_clone.ui.dashboard.home.component.TrendingNow
import com.example.netflix_clone.ui.dashboard.home.component.onBottomSheetTapped
import com.example.netflix_clone.ui.theme.NetflixTheme
import com.example.netflix_clone.util.Resource.Success
import com.example.netflix_clone.ui.dashboard.home.component.PopularOnNetflix
import kotlinx.coroutines.CoroutineScope

@ExperimentalMaterialApi
@Composable
fun Home(
  bottomSheetScaffoldState: BottomSheetScaffoldState,
  modifier: Modifier = Modifier,
  coroutineScope: CoroutineScope,
  scrollState: LazyListState
) {
  val selectedMovieViewModel = ViewModelProvider.selectedMovieViewModel
  NetflixSurface(
    color = NetflixTheme.colors.appBackground,
  ) {
    LazyColumn(
      state = scrollState,
      modifier = modifier
        .padding(bottom = 120.dp)
    ) {
      item {
        when (val topHighlightedMovie = ViewModelProvider.movieByIdViewModel.movie) {
          is Success -> {
            TopHighlightedMovie(
              onMovieClick = {
                selectedMovieViewModel.setSelectedMovie(topHighlightedMovie.data)
                onBottomSheetTapped(
                  coroutineScope = coroutineScope,
                  bottomSheetScaffoldState = bottomSheetScaffoldState
                )
              },
              modifier = modifier,
              topMovie = topHighlightedMovie.data
            )
          }

          else -> {}
        }
        Spacer(modifier = Modifier.height(10.dp))
        when (val netflixOriginals = ViewModelProvider.topRatedMoviesViewModel.topRatedMovies) {
          is Success -> {
            NetflixOriginals(
              onMovieClick = { movieId ->
                selectedMovieViewModel.setSelectedMovie(netflixOriginals.data.find { it.id == movieId })
                onBottomSheetTapped(
                  coroutineScope = coroutineScope,
                  bottomSheetScaffoldState = bottomSheetScaffoldState
                )
              },
              modifier = modifier,
              netflixOriginalMovies = netflixOriginals.data
            )
          }

          else -> {}
        }
        Spacer(modifier = Modifier.height(20.dp))
        when (val popularOnNetFlix = ViewModelProvider.popularMoviesViewModel.popularMovies) {
          is Success -> {
            PopularOnNetflix(
              onMovieClick = { movieId: Long ->
                selectedMovieViewModel.setSelectedMovie(popularOnNetFlix.data.find { it.id == movieId })
                onBottomSheetTapped(
                  coroutineScope = coroutineScope,
                  bottomSheetScaffoldState = bottomSheetScaffoldState
                )
              },
              modifier = modifier,
              popularOnNetflixMovies = popularOnNetFlix.data
            )
          }

          else -> {}
        }
        Spacer(modifier = Modifier.height(20.dp))
        when (val trendingNow = ViewModelProvider.nowPlayingMoviesViewModel.nowPlayingMovies) {
          is Success -> {
            TrendingNow(
              onMovieClick = { movieId ->
                selectedMovieViewModel.setSelectedMovie(trendingNow.data.find { it.id == movieId })
                onBottomSheetTapped(
                  coroutineScope = coroutineScope,
                  bottomSheetScaffoldState = bottomSheetScaffoldState
                )
              },
              modifier = modifier,
              trendingNowMovies = trendingNow.data
            )
          }

          else -> {}
        }
      }
    }
  }
}

@Composable
private fun TopHighlightedMovie(
  onMovieClick: (Long) -> Unit,
  modifier: Modifier = Modifier,
  topMovie: Movie
) {
  ConstraintLayout {
    // Create references for the composables to constrain
    val (movieImage, buttonPanel, topTrendingBanner) = createRefs()
    HighlightMovieItem(topMovie, onMovieClick,
      modifier = modifier.constrainAs(movieImage) {
        top.linkTo(parent.top)
      }
    )
    TopTrendingBanner(
      modifier = modifier.constrainAs(topTrendingBanner) {
        bottom.linkTo(buttonPanel.top, margin = 24.dp)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
      }
    )
    Row(
      modifier = modifier
        .constrainAs(buttonPanel) {
          bottom.linkTo(parent.bottom, margin = 32.dp)
        }
    ) {
      MyListButton(modifier = modifier.weight(1f))
      PlayButton(isPressed =  mutableStateOf(true), modifier = modifier.weight(1f))
      InfoButton(modifier = modifier.weight(1f))
    }
  }
}

@Composable
private fun TopTrendingBanner(
  modifier: Modifier
) {
  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Card(
      color = NetflixTheme.colors.banner,
      shape = RoundedCornerShape(10),
      modifier = modifier
        .size(
          width = 28.dp,
          height = 28.dp
        )
    ) {
      Column(
        modifier = Modifier
          .padding(2.dp)
          .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Text(
          text = "TOP",
          fontSize = 8.sp,
          style = MaterialTheme.typography.button,
          maxLines = 1
        )
        Text(
          text = "10",
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          style = MaterialTheme.typography.button,
          maxLines = 1
        )
      }
    }
    Spacer(modifier = Modifier.width(4.dp))
    Text(
      text = "#2 in India Today",
      color = NetflixTheme.colors.textPrimary,
      fontSize = 14.sp,
      letterSpacing = (-0.10).sp,
      fontWeight = FontWeight.Bold,
      style = MaterialTheme.typography.button,
      maxLines = 1
    )
  }
}

@Preview("Top Highlighted Movie Preview")
@Composable
fun TopHighlightedMoviePreview() {
  NetflixTheme(
    darkTheme = true
  ) {
    TopHighlightedMovie(onMovieClick = {}, topMovie = movies.first())
  }
}

@Preview("Netflix Originals Preview")
@Composable
fun NetflixOriginalsPreview() {
  NetflixTheme(
    darkTheme = true
  ) {
    NetflixOriginals(onMovieClick = {}, netflixOriginalMovies = movies)
  }
}

@Preview("Popular On Netflix Preview")
@Composable
fun PopularOnNetflixPreview() {
  NetflixTheme(
    darkTheme = true
  ) {
    PopularOnNetflix(onMovieClick = {}, popularOnNetflixMovies = movies)
  }
}