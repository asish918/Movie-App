package com.example.netflix_clone.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.netflix_clone.ui.theme.NetflixTheme

@Composable
fun Card(
  modifier: Modifier = Modifier,
  shape: Shape = MaterialTheme.shapes.medium,
  color: Color = NetflixTheme.colors.uiBackground,
  contentColor: Color = NetflixTheme.colors.textPrimary,
  border: BorderStroke? = null,
  elevation: Dp = 1.dp,
  content: @Composable () -> Unit
) {
  NetflixSurface(
    modifier = modifier,
    shape = shape,
    color = color,
    contentColor = contentColor,
    elevation = elevation,
    border = border,
    content = content
  )
}
