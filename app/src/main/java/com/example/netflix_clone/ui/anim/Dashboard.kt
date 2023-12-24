package com.example.netflix_clone.ui.anim

import androidx.compose.animation.animateColorAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.graphics.Color
import com.example.netflix_clone.ui.theme.NetflixTheme

@Composable
fun getIconTint(selected: Boolean): State<Color> {
  return animateColorAsState(
    if (selected) {
      NetflixTheme.colors.iconInteractive
    } else {
      NetflixTheme.colors.iconInteractiveInactive
    }, label = ""
  )
}