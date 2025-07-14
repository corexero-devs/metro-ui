package org.corexero.metroui.domain.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter

data class MetroBrandConfig(
    val brandHeaderName: String,
    val disclaimer: String,
    val mapPainter: @Composable () -> Painter,
    val quickAccessFeatures: QuickAccessFeatures = QuickAccessFeatures()
) {

    data class QuickAccessFeatures(
        val bookTicket: Boolean = true,
        val nearestMetro: Boolean = true,
        val timing: Boolean = true,
        val map: Boolean = true
    )

}
