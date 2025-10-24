package com.example.masakin.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Red70,
    secondary = Grey30,
    tertiary = Red50,
    background = Grey50,
    surface = Grey40,
    onPrimary = White,
    onSecondary = White,
    onTertiary = White,
    onBackground = Grey10,
    onSurface = Grey10
)

private val LightColorScheme = lightColorScheme(
    primary = Red50,
    secondary = Grey20,
    tertiary = Red30,
    background = Red10,
    surface = White,
    onPrimary = White,
    onSecondary = Black,
    onTertiary = White,
    onBackground = Black,
    onSurface = Black
)

@Composable
fun MasakinTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}