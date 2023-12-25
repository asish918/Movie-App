package com.example.netflix_clone.ui.dashboard.home.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.netflix_clone.ui.viewmodel.ViewModelProvider
import com.example.netflix_clone.domain.repo.movies
import com.example.netflix_clone.R.string
import com.example.netflix_clone.domain.model.Movie
import com.example.netflix_clone.ui.components.NetflixSurface
import com.example.netflix_clone.ui.components.SmallMovieItem
import com.example.netflix_clone.ui.theme.NetflixTheme
import com.example.netflix_clone.util.Resource.Success
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun BottomSheetContent(
  onMovieClick: (Long) -> Unit,
  onBottomSheetClosePressed: () -> Unit
) {
  when (val selectedMovie = ViewModelProvider.selectedMovieViewModel.selectedMovie) {
    is Success<*> -> {
      selectedMovie.data?.let { safeSelectedMovie: Any ->
        BottomSheetLayout(
          selectedMovie = safeSelectedMovie as Movie,
          onMovieClick = onMovieClick,
          onBottomSheetClosePressed = onBottomSheetClosePressed
        )
      }
    }

    else -> {}
  }
}

@SuppressLint("UnrememberedMutableState")
@Composable
private fun BottomSheetLayout(
  selectedMovie: Movie,
  onMovieClick: (Long) -> Unit,
  onBottomSheetClosePressed: () -> Unit
) {
  NetflixSurface(
    shape = RoundedCornerShape(topStartPercent = 5, topEndPercent = 5),
    color = NetflixTheme.colors.uiLightBackground,
    modifier = Modifier
      .wrapContentWidth()
      .height(350.dp)
      .clickable { onMovieClick(selectedMovie.id) }
  ) {
    NetflixSurface(
      shape = RoundedCornerShape(topStartPercent = 5, topEndPercent = 5),
      color = NetflixTheme.colors.uiLightBackground,
      modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
    ) {
      Column {
        Row(modifier = Modifier.padding(top = 12.dp, start = 12.dp, end = 12.dp, bottom = 6.dp)) {
          SmallMovieItem(
            selectedMovie,
            onMovieSelected = onMovieClick
          )
          Spacer(modifier = Modifier.width(12.dp))
          Column {
            Row(
              modifier = Modifier.fillMaxWidth()
            ) {
              Column(
                modifier = Modifier.weight(1f)
              ) {
                Text(
                  text = selectedMovie.title,
                  fontSize = 20.sp,
                  fontWeight = FontWeight.ExtraBold,
                  maxLines = 1
                )
                Spacer(modifier = Modifier.height(2.dp))
                Row {
                  Text(
                    text = selectedMovie.releaseDate.substring(0, 4),
                    color = NetflixTheme.colors.textSecondaryDark,
                    fontSize = 12.sp,
                    maxLines = 1
                  )
                  Spacer(modifier = Modifier.width(12.dp))
                  Text(
                    text = selectedMovie.avgVote.toString(),
                    color = NetflixTheme.colors.textSecondaryDark,
                    fontSize = 12.sp,
                    maxLines = 1
                  )
                  Spacer(modifier = Modifier.width(12.dp))
                  Text(
                    text = selectedMovie.voteCount.toString(),
                    color = NetflixTheme.colors.textSecondaryDark,
                    fontSize = 12.sp,
                    maxLines = 1
                  )
                }
              }
              IconButton(
                onClick = { onBottomSheetClosePressed() },
                modifier = Modifier
                  .clip(CircleShape)
                  .background(color = NetflixTheme.colors.uiLighterBackground)
                  .size(25.dp)
              ) {
                Icon(
                  imageVector = Outlined.Close,
                  tint = NetflixTheme.colors.iconTint,
                  contentDescription = null,
                )
              }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = selectedMovie.overview,
              fontSize = 14.sp,
              maxLines = 5,
              lineHeight = 18.sp,
              overflow = Ellipsis
            )
          }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(modifier = Modifier.padding(top = 6.dp, start = 12.dp, end = 12.dp, bottom = 12.dp)) {
          PlayButton(
            isPressed = mutableStateOf(true),
            modifier = Modifier.weight(2f)
          )
          IconTextButton(
            buttonIcon = Outlined.ArrowDropDown,
            buttonText = stringResource(id = string.download),
            onButtonClick = {},
            modifier = Modifier.weight(1f),
          )
          IconTextButton(
            buttonIcon = Outlined.PlayArrow,
            buttonText = stringResource(id = string.preview),
            onButtonClick = {},
            modifier = Modifier.weight(1f),
          )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Divider(color = NetflixTheme.colors.uiLighterBackground, thickness = 1.dp)
        EpisodesAndInfo(
          modifier = Modifier.padding(
            top = 12.dp,
            start = 12.dp,
            end = 12.dp,
            bottom = 12.dp
          )
        )
      }
    }
  }
}

@Composable
fun IconTextButton(
  buttonIcon: ImageVector,
  buttonText: String,
  onButtonClick: () -> Unit,
  modifier: Modifier
) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier.selectable(selected = false, onClick = { onButtonClick() })
  ) {
    Icon(
      imageVector = buttonIcon,
      contentDescription = null
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
      text = buttonText,
      fontSize = 10.sp,
      style = MaterialTheme.typography.button,
      maxLines = 1
    )
  }
}

@Composable
fun EpisodesAndInfo(modifier: Modifier) {
  Row(modifier = modifier) {
    Icon(
      imageVector = Outlined.Info,
      contentDescription = null,
      modifier = Modifier.weight(1f)
    )
    Text(
      text = stringResource(id = string.episodesAndInfo),
      modifier = Modifier
        .weight(10f)
        .align(Alignment.CenterVertically)
        .padding(start = 4.dp),
      fontSize = 12.sp,
      style = MaterialTheme.typography.button,
      maxLines = 1
    )
    Icon(
      imageVector = Outlined.ArrowForward,
      modifier = Modifier
        .weight(1f)
        .size(20.dp),
      contentDescription = null
    )
  }
}

@ExperimentalMaterialApi
fun onBottomSheetTapped(
  coroutineScope: CoroutineScope,
  bottomSheetScaffoldState: BottomSheetScaffoldState
) {
  coroutineScope.launch {
    try {
      if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
        bottomSheetScaffoldState.bottomSheetState.expand()
      } else {
        bottomSheetScaffoldState.bottomSheetState.collapse()
      }
    } catch (e: IllegalArgumentException) {
      Timber.e("Exception in Bottom Sheet: ${e.message}")
    }
  }
}

@Preview("Bottom Sheet Content")
@Composable
fun BottomSheetContentPreview() {
  NetflixTheme {
    BottomSheetLayout(
      selectedMovie = movies.last(),
      onMovieClick = {},
      onBottomSheetClosePressed = {}
    )
  }
}