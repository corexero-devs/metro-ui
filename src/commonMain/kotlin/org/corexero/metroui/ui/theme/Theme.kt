package org.corexero.metroui.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import jaipurmetro.metroui.generated.resources.Res
import jaipurmetro.metroui.generated.resources.inter_black
import jaipurmetro.metroui.generated.resources.inter_bold
import jaipurmetro.metroui.generated.resources.inter_extra_bold
import jaipurmetro.metroui.generated.resources.inter_extra_light
import jaipurmetro.metroui.generated.resources.inter_light
import jaipurmetro.metroui.generated.resources.inter_medium
import jaipurmetro.metroui.generated.resources.inter_regular
import jaipurmetro.metroui.generated.resources.inter_semi_bold
import jaipurmetro.metroui.generated.resources.inter_thin
import org.jetbrains.compose.resources.Font

val interFont
    @Composable
    get() = FontFamily(
        Font(Res.font.inter_thin, FontWeight.Thin),
        Font(Res.font.inter_light, FontWeight.Light),
        Font(Res.font.inter_extra_light, FontWeight.ExtraLight),
        Font(Res.font.inter_regular, FontWeight.Normal),
        Font(Res.font.inter_medium, FontWeight.Medium),
        Font(Res.font.inter_semi_bold, FontWeight.SemiBold),
        Font(Res.font.inter_bold, FontWeight.Bold),
        Font(Res.font.inter_extra_bold, FontWeight.ExtraBold),
        Font(Res.font.inter_black, FontWeight.Black),
    )

val blueDark = Color(0xFF0052A5)
val greenDotColor = Color(0xFF22C55E)
val redDotColor = Color(0xFFEF4444)
val subHeadingTitle = Color(0xFF6B7280)
val backgroundColor = Color(0xFFF5F7FA)
val greyishColor = Color(0xFFE5E7EB)

private val LightColors = lightColors(
    primary = Color(0xFF2574EE),
    secondary = Color(0xFF656565),
    error = Color(0xFFFE6E71)

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
fun MetroTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = LightColors,
        content = content
    )
}