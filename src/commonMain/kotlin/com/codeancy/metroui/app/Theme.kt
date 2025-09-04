package com.codeancy.metroui.app

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import indianmetro.metroui.generated.resources.Res
import indianmetro.metroui.generated.resources.inter_black
import indianmetro.metroui.generated.resources.inter_bold
import indianmetro.metroui.generated.resources.inter_extra_bold
import indianmetro.metroui.generated.resources.inter_extra_light
import indianmetro.metroui.generated.resources.inter_light
import indianmetro.metroui.generated.resources.inter_medium
import indianmetro.metroui.generated.resources.inter_regular
import indianmetro.metroui.generated.resources.inter_semi_bold
import indianmetro.metroui.generated.resources.inter_thin
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

private val LightColors = lightColorScheme(
    primary = Color(0xFF2574EE),
    secondary = Color(0xFF656565),
    error = Color(0xFFFE6E71)
)

@Composable
fun MetroTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = MaterialTheme.typography.let { typography ->
            typography.copy(
                bodyLarge = typography.bodyLarge.copy(fontFamily = interFont),
                bodyMedium = typography.bodyMedium.copy(fontFamily = interFont),
                bodySmall = typography.bodySmall.copy(fontFamily = interFont),
                labelLarge = typography.labelLarge.copy(fontFamily = interFont),
                labelMedium = typography.labelMedium.copy(fontFamily = interFont),
                labelSmall = typography.labelSmall.copy(fontFamily = interFont),
                titleLarge = typography.titleLarge.copy(fontFamily = interFont),
                titleMedium = typography.titleMedium.copy(fontFamily = interFont),
                titleSmall = typography.titleSmall.copy(fontFamily = interFont),
                displayLarge = typography.displayLarge.copy(fontFamily = interFont),
                displayMedium = typography.displayMedium.copy(fontFamily = interFont),
                displaySmall = typography.displaySmall.copy(fontFamily = interFont),
                headlineLarge = typography.headlineLarge.copy(fontFamily = interFont),
                headlineMedium = typography.headlineMedium.copy(fontFamily = interFont),
                headlineSmall = typography.headlineSmall.copy(fontFamily = interFont),
            )
        },
        content = content
    )
}