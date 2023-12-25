package com.example.netflix_clone.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue.Collapsed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.netflix_clone.ui.anim.getFabTextTextPaddingState
import com.example.netflix_clone.ui.components.JetFlixTopAppBar
import com.example.netflix_clone.ui.theme.NetflixTheme
import com.example.netflix_clone.ui.components.JetFlixScaffold
import com.example.netflix_clone.ui.dashboard.DashboardSections
import com.example.netflix_clone.ui.dashboard.JetFlixBottomBar
import com.example.netflix_clone.ui.dashboard.home.component.BottomSheetContent
import com.example.netflix_clone.ui.navigation.NavGraph
import com.example.netflix_clone.ui.navigation.MainActions
import com.example.netflix_clone.ui.viewmodel.ProvideMultiViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@ExperimentalAnimationApi
@Composable
fun NetflixApp() {
        NetflixTheme {
            ProvideMultiViewModel {


                val (shouldShowAppBar, updateAppBarVisibility) = remember { mutableStateOf(true) }
                val navController = rememberNavController()
                val tabs = remember { DashboardSections.values() }
                val bottomSheetCoroutineScope = rememberCoroutineScope()
                val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
                    bottomSheetState = BottomSheetState(Collapsed)
                )
                val homeScreenScrollState = rememberLazyListState()
                val isScrolledDown = remember {
                    derivedStateOf {
                        homeScreenScrollState.firstVisibleItemScrollOffset > 0
                    }
                }
                val mainNavActions = remember(navController) {
                    MainActions(navController, updateAppBarVisibility)
                }

                BottomSheetScaffold(
                    scaffoldState = bottomSheetScaffoldState,
                    sheetContent = {
                        BottomSheetContent(
                            onMovieClick = { movieId: Long ->
                                closeBottomSheet(bottomSheetCoroutineScope, bottomSheetScaffoldState)
                                mainNavActions.openMovieDetails(movieId)
                            },
                            onBottomSheetClosePressed = {
                                closeBottomSheet(bottomSheetCoroutineScope, bottomSheetScaffoldState)
                            }
                        )
                    },
                    sheetPeekHeight = 0.dp
                ) {
                    JetFlixScaffold(
                        bottomBar = { JetFlixBottomBar(navController = navController, tabs = tabs) },
                        fab = {
                            if (shouldShowAppBar) {
                                PlaySomethingFAB(isScrolledUp = isScrolledDown.value.not())
                            }
                        }
                    ) { innerPaddingModifier ->

                        NavGraph(
                            navController = navController,
                            modifier = Modifier.padding(innerPaddingModifier),
                            bottomSheetScaffoldState = bottomSheetScaffoldState,
                            bottomSheetCoroutineScope = bottomSheetCoroutineScope,
                            homeScreenScrollState = homeScreenScrollState,
                            mainNavActions = mainNavActions
                        )
                        if (shouldShowAppBar) {
                            JetFlixTopAppBar(isScrolledDown = isScrolledDown.value)
                        }
                    }
                }
            }
        }
}

@OptIn(ExperimentalMaterialApi::class)
private fun closeBottomSheet(
    bottomSheetCoroutineScope: CoroutineScope,
    bottomSheetScaffoldState: BottomSheetScaffoldState
) {
    bottomSheetCoroutineScope.launch {
        bottomSheetScaffoldState.bottomSheetState.collapse()
    }
}

@ExperimentalAnimationApi
@Composable
fun PlaySomethingFAB(
    isScrolledUp: Boolean
) {
    FloatingActionButton(
        onClick = {},
        containerColor = NetflixTheme.colors.progressIndicatorBg,
        elevation = FloatingActionButtonDefaults.elevation(8.dp),
    ) {
        val fabTextPadding = getFabTextTextPaddingState(isScrolledDown = isScrolledUp.not()).value
        Box(
            modifier = Modifier.padding(
                start = fabTextPadding,
                end = fabTextPadding,
            ),
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Filled.Add,
                    contentDescription = "Shuffle",
                    tint = NetflixTheme.colors.iconTint
                )
                Spacer(Modifier.width(fabTextPadding))
                AnimatedVisibility(visible = isScrolledUp) {
                    Text(
                        text = "Play Something",
                        color = NetflixTheme.colors.uiLightBackground,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Preview("Play Something FAB Preview")
@Composable
fun PlaySomethingFloatingActionButtonPreview() {
    NetflixTheme(
        darkTheme = true
    ) {
        PlaySomethingFAB(true)
    }
}