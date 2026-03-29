package id.feinn.feinnnearby.ui.theme

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = primary400,
    onPrimary = onPrimary,
    primaryContainer = primary700,
    onPrimaryContainer = primary100,
    inversePrimary = primary600,
    secondary = secondary400,
    onSecondary = onPrimary,
    secondaryContainer = secondary800,
    onSecondaryContainer = secondary100,
    tertiary = tertiary300,
    onTertiary = onPrimary,
    tertiaryContainer = tertiary700,
    onTertiaryContainer = tertiary50,
    background = background,
    onBackground = onSurface,
    surface = glassSurface,
    onSurface = onSurface,
    surfaceVariant = neutral800,
    onSurfaceVariant = onSurfaceVariant,
    surfaceTint = Color.Transparent,
    inverseSurface = onSurface,
    inverseOnSurface = onPrimary,
    error = error,
    onError = onError,
    errorContainer = errorContainer,
    onErrorContainer = onErrorContainer,
    outline = glassBorder,
    outlineVariant = borderSubtle,
    scrim = scrim,
    surfaceBright = backgroundElevated,
    surfaceContainer = glassSurface,
    surfaceContainerHigh = surfaceContainerHigh,
    surfaceContainerHighest = glassSurfaceStrong,
    surfaceContainerLow = surfaceContainerLow,
    surfaceContainerLowest = surfaceContainerLowest,
    surfaceDim = background,
    primaryFixed = primary200,
    primaryFixedDim = primary400,
    onPrimaryFixed = onPrimary,
    onPrimaryFixedVariant = primary800,
    secondaryFixed = secondary200,
    secondaryFixedDim = secondary400,
    onSecondaryFixed = onPrimary,
    onSecondaryFixedVariant = secondary800,
    tertiaryFixed = tertiary200,
    tertiaryFixedDim = tertiary300,
    onTertiaryFixed = onPrimary,
    onTertiaryFixedVariant = tertiary700,
)

private val LightColorScheme = lightColorScheme(


    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun FeinnNearbyTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
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