package hernanbosqued.frontend.ui

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

// Colores para el tema claro
val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)
val LightBackground = Color.White
val LightSurface = Color.White
val OnLightPrimary = Color.White
val OnLightSecondary = Color.Black
val OnLightBackground = Color.Black
val OnLightSurface = Color.Black

// Colores para el tema oscuro
val DarkPurple200 = Color(0xFFBB86FC)
val DarkPurple700 = Color(0xFF3700B3)
val DarkTeal200 = Color(0xFF03DAC5)
val DarkBackground = Color(0xFF121212)
val DarkSurface = Color(0xFF1E1E1E)
val OnDarkPrimary = Color.Black
val OnDarkSecondary = Color.Black
val OnDarkBackground = Color.White
val OnDarkSurface = Color.White

val DarkColorPalette = darkColors(
    primary = DarkPurple200,
    primaryVariant = DarkPurple700,
    secondary = DarkTeal200,
    background = DarkBackground,
    surface = DarkSurface,
    onPrimary = OnDarkPrimary,
    onSecondary = OnDarkSecondary,
    onBackground = OnDarkBackground,
    onSurface = OnDarkSurface,
)

val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200,
    background = LightBackground,
    surface = LightSurface,
    onPrimary = OnLightPrimary,
    onSecondary = OnLightSecondary,
    onBackground = OnLightBackground,
    onSurface = OnLightSurface,
)