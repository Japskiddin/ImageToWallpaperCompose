package io.github.japskiddin.imagetowallpaper

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.github.japskiddin.imagetowallpaper.ui.screens.HomeScreen
import io.github.japskiddin.imagetowallpaper.ui.theme.ImageToWallpaperTheme

const val ROUTE_HOME = "home"

sealed class Screen(val route: String, @StringRes val titleId: Int) {
    data object Home : Screen(ROUTE_HOME, R.string.app_name)
}

@Composable
fun AppScreen(
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val navController = rememberNavController()

        Scaffold(
            topBar = {
                val backStackEntry by navController.currentBackStackEntryAsState()
                val currentScreen = when (backStackEntry?.destination?.route) {
                    ROUTE_HOME -> Screen.Home
                    else -> Screen.Home
                }
                ToolBar(
                    screen = currentScreen,
                    canNavigateBack = navController.previousBackStackEntry != null,
                    navigateUp = { navController.navigateUp() })
            },
            content = { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = Screen.Home.route,
                    modifier = modifier.padding(innerPadding)
                ) {
                    composable(route = Screen.Home.route) {
                        HomeScreen()
                    }
                }
            },
            modifier = modifier
        )
    }
}

@Composable
fun ToolBar(
    screen: Screen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(text = stringResource(id = screen.titleId)) },
        actions = {
            if (screen.route == ROUTE_HOME) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Rounded.Settings,
                        contentDescription = stringResource(id = R.string.settings),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back)
                    )
                }
            }
        },
        modifier = modifier
    )
}

@Preview(
    name = "Light mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    showSystemUi = true
)
@Preview(
    name = "Dark mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    showSystemUi = true
)
@Composable
fun AppScreenPreview() {
    ImageToWallpaperTheme(dynamicColor = false) {
        AppScreen()
    }
}

@Preview
@Composable
fun ToolBarPreview() {
    ImageToWallpaperTheme(dynamicColor = false) {
        ToolBar(
            screen = Screen.Home,
            canNavigateBack = false,
            navigateUp = { /*TODO*/ }
        )
    }
}