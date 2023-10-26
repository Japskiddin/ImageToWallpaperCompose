package io.github.japskiddin.imagetowallpapercompose.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.japskiddin.imagetowallpapercompose.R
import io.github.japskiddin.imagetowallpapercompose.data.repository.AppTheme
import io.github.japskiddin.imagetowallpapercompose.data.repository.DEFAULT_PREFERENCE
import io.github.japskiddin.imagetowallpapercompose.data.repository.SettingsRepository
import io.github.japskiddin.imagetowallpapercompose.ui.components.SettingsItem
import io.github.japskiddin.imagetowallpapercompose.ui.theme.ImageToWallpaperTheme

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit,
    viewModel: SettingsViewModel = viewModel(
        factory = SettingsViewModelFactory(
            repository = SettingsRepository(
                LocalContext.current
            )
        )
    )
) {
    Scaffold(
        topBar = {
            SettingsToolBar(
                onNavigateUp = onNavigateUp,
                modifier = modifier
            )
        },
        content = { innerPadding ->
            Column(
                modifier = modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                val appPreferences by viewModel.appPreferences.collectAsState(DEFAULT_PREFERENCE)
                SettingsItem(
                    title = stringResource(id = R.string.aspect_ratio),
                    description = appPreferences.aspectRatio.toString(),
                    onClick = {

                    }
                )
                Divider(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp)
                        .height(1.dp)
                )
                SettingsItem(
                    title = stringResource(id = R.string.app_theme),
                    description = stringResource(
                        id = when (appPreferences.appTheme) {
                            AppTheme.MODE_DAY -> R.string.app_theme_day
                            AppTheme.MODE_NIGHT -> R.string.app_theme_night
                            AppTheme.MODE_AUTO -> R.string.app_theme_auto
                        }
                    ),
                    onClick = {

                    }
                )
            }
        },
        modifier = modifier.fillMaxSize()
    )
}

// TODO: add shadow

@Composable
fun SettingsToolBar(
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.settings)) },
        navigationIcon = {
            IconButton(onClick = onNavigateUp) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.back)
                )
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
fun SettingsScreenPreview() {
    ImageToWallpaperTheme(dynamicColor = false) {
        SettingsScreen(onNavigateUp = {})
    }
}