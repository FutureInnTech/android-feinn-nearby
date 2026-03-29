package id.feinn.feinnnearby.ui.theme

import androidx.compose.ui.graphics.Color

val background        = Color(0xFF0A0E1A) // deep navy-black
val backgroundSoft    = Color(0xFF0F172A)
val backgroundElevated= Color(0xFF111827)

val primary50  = Color(0xFFF0FBFF)
val primary100 = Color(0xFFE0F7FF)
val primary200 = Color(0xFFBFEFFF)
val primary300 = Color(0xFF99E4FF)
val primary400 = Color(0xFF7DD3FC) // base
val primary500 = Color(0xFF5CC8F8)
val primary600 = Color(0xFF38BDF8)
val primary700 = Color(0xFF1FA4E0)
val primary800 = Color(0xFF147CA8)
val primary900 = Color(0xFF0B4F6C)

val secondary50  = Color(0xFFF2F7FA)
val secondary100 = Color(0xFFE3EEF5)
val secondary200 = Color(0xFFC7DCE8)
val secondary300 = Color(0xFFA8C8D9)
val secondary400 = Color(0xFF88B4CC) // base
val secondary500 = Color(0xFF6FA2BD)
val secondary600 = Color(0xFF5A8EA8)
val secondary700 = Color(0xFF48738A)
val secondary800 = Color(0xFF36576A)
val secondary900 = Color(0xFF243C4A)

val tertiary50  = Color(0xFFF6F0FF)
val tertiary100 = Color(0xFFEDE0FF)
val tertiary200 = Color(0xFFD8BFFF)
val tertiary300 = Color(0xFFC8A0F0) // base
val tertiary400 = Color(0xFFB084E8)
val tertiary500 = Color(0xFF9B6DD6)
val tertiary600 = Color(0xFF7C4BB8)
val tertiary700 = Color(0xFF5B3690)
val tertiary800 = Color(0xFF3E2466)
val tertiary900 = Color(0xFF281843)

val neutral50  = Color(0xFFF5F7FA)
val neutral100 = Color(0xFFE5EAF2)
val neutral200 = Color(0xFFCBD5E1)
val neutral300 = Color(0xFF94A3B8)
val neutral400 = Color(0xFF64748B)
val neutral500 = Color(0xFF475569)
val neutral600 = Color(0xFF334155)
val neutral700 = Color(0xFF1E293B)
val neutral800 = Color(0xFF1A2438) // base
val neutral900 = Color(0xFF0F172A)

// Layer 1 (default glass)
val glassSurface       = Color(0x99101524) // ~60% opacity
val glassBorder        = Color(0x1A7DD3FC) // primary 10%
val glassHighlight     = Color(0x33FFFFFF) // subtle light

// Layer 2 (elevated)
val glassSurfaceStrong = Color(0xBF101524) // ~75%
val glassBorderStrong  = Color(0x267DD3FC) // ~15%

// Overlay / frost
val glassFrost         = Color(0x66FFFFFF)

val onSurface          = Color(0xFFE5E7EB) // main text
val onSurfaceVariant   = Color(0xFF9CA3AF) // secondary text
val onPrimary          = Color(0xFF0A0E1A)

val glowPrimary   = Color(0x0D7DD3FC) // soft glow
val glowLavender  = Color(0x0DC8A0F0)

val borderSubtle  = Color(0x14FFFFFF) // 8%
val borderSoft    = Color(0x26FFFFFF) // 15%

val gradientPrimary = listOf(
    Color(0xFF7DD3FC),
    Color(0xFF38BDF8),
    Color(0xFF1FA4E0)
)

val gradientGlass = listOf(
    Color(0x66FFFFFF),
    Color(0x0D7DD3FC)
)

val error = Color(0xFFEF4444)
val onError = Color(0xFFFFFFFF)
val errorContainer = Color(0xFF7F1D1D)
val onErrorContainer = Color(0xFFFEE2E2)

val scrim = Color(0x80000000)
val surfaceContainerHigh = Color(0xB3101524)
val surfaceContainerLow = Color(0x80101524)
val surfaceContainerLowest = Color(0x66101524) // subtle glass