package com.example.netflix_clone.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.netflix_clone.ui.theme.NetflixTheme

@Composable
fun CircularRemoteImage(
  imageUrl: String,
  modifier: Modifier = Modifier,
  elevation: Dp = 0.dp
) {
  NetflixSurface(
    color = Color.LightGray,
    elevation = elevation,
    shape = CircleShape,
    modifier = modifier
  ) {
    Image(
      painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
          .data(imageUrl)
          .crossfade(true)
          .build(),
      ),
      contentScale = ContentScale.Crop,
      modifier = Modifier.fillMaxSize(),
      contentDescription = null
    )
  }
}

@Composable
fun CircularLocalImage(
  resId: Int,
  modifier: Modifier = Modifier,
  elevation: Dp = 0.dp
) {
  NetflixSurface(
    color = Color.LightGray,
    elevation = elevation,
    shape = CircleShape,
    modifier = modifier
  ) {
    Image(
      painterResource(resId),
      contentDescription = null,
      contentScale = ContentScale.Crop,
      modifier = Modifier.fillMaxSize()
    )
  }
}

@Composable
fun RoundedCornerRemoteImage(
  imageUrl: String,
  modifier: Modifier = Modifier,
  cornerPercent: Int,
  elevation: Dp = 0.dp
) {
  NetflixSurface(
    color = Color.LightGray,
    elevation = elevation,
    shape = RoundedCornerShape(cornerPercent),
    modifier = modifier
  ) {
    Image(
      painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
          .data(imageUrl)
          .crossfade(true)
          .build(),
      ),
      contentScale = ContentScale.Crop,
      modifier = Modifier.fillMaxSize(),
      contentDescription = null
    )
  }
}

@Composable
fun FullScreenRemoteImage(
  imageUrl: String,
  modifier: Modifier = Modifier
) {
  NetflixSurface(
    color = Color.LightGray,
    elevation = 0.dp,
    shape = RectangleShape,
    modifier = modifier
  ) {
    Image(
      painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
          .data(imageUrl)
          .crossfade(true)
          .build(),
      ),
      contentScale = ContentScale.Crop,
      modifier = Modifier.fillMaxSize(),
      contentDescription = null
    )
  }
}



@Preview("Circular App Image")
@Composable
fun CircularRemoteImagePreview() {
  NetflixTheme {
    CircularRemoteImage(
      imageUrl = "",
      modifier = Modifier
        .size(120.dp),
      elevation = 10.dp
    )
  }
}

@Preview("Rounded Corner App Image")
@Composable
fun RoundedCornerRemoteImagePreview() {
  NetflixTheme {
    RoundedCornerRemoteImage(
      imageUrl = "",
      modifier = Modifier
        .size(120.dp),
      cornerPercent = 5,
      elevation = 10.dp
    )
  }
}

@Preview("Full Screen Remote Image")
@Composable
fun FullScreenRemoteImagePreview() {
  NetflixTheme {
    FullScreenRemoteImage(
      imageUrl = "",
      modifier = Modifier
        .size(120.dp)
    )
  }
}