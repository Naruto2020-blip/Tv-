package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val TvDarkColorScheme = darkColorScheme(
    primary = CrBlue,
    onPrimary = Color.White,
    primaryContainer = Color(0xFF002366),
    onPrimaryContainer = Color(0xFFD6E4FF),
    secondary = CrRed,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF5C000B),
    onSecondaryContainer = Color(0xFFFFDAD6),
    tertiary = CrGold,
    onTertiary = Color.Black,
    background = BackgroundDark,
    onBackground = TextPrimary,
    surface = SurfaceDark,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = TextSecondary,
    outline = BorderColor
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = TvDarkColorScheme,
        typography = Typography,
        content = content
    )
}
