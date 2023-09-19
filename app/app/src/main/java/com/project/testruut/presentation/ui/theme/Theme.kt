package com.project.testruut.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val DarkColorPalette = darkColorScheme(
    primary = OrangeLight,
    background = BlueBackground,
    onPrimary = Color.White,
    onBackground = BlueBackground, secondary = Color.Red
)

@Composable
fun TestRUUTTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = DarkColorPalette,
        typography = Typography,
        content = content
    )
}