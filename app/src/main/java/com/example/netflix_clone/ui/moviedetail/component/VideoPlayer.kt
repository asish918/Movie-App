package com.example.netflix_clone.ui.moviedetail.component

import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView

@OptIn(UnstableApi::class) @Composable
fun VideoPlayer(url: String) {
  val context = LocalContext.current

  val exoPlayer = remember {
    ExoPlayer.Builder(context)
      .build()
      .apply {
        val dataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(
          context,
        )

        val mediaItem = MediaItem.fromUri(url)
        val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
          .createMediaSource(mediaItem)
        this.setMediaSource(mediaSource, true)
        this.prepare()
      }
  }

  exoPlayer.playWhenReady = true
  exoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
  exoPlayer.repeatMode = Player.REPEAT_MODE_ONE

  DisposableEffect(
    AndroidView(
      factory = {
        PlayerView(context).apply {
          useController = true
          resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT

          player = exoPlayer
          layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        }
      },
      modifier = Modifier.fillMaxWidth()
    )
  ) {
    onDispose {
      exoPlayer.release()
    }
  }
}