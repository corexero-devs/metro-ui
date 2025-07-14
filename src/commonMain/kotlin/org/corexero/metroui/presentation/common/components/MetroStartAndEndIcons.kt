package org.corexero.metroui.presentation.common.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MetroStartIcon() {
    Box(
        modifier = Modifier
            .size(8.dp)
            .clip(CircleShape)
            .border(
                2.dp,
                Color.Green,
                CircleShape
            )
    )
}

@Composable
fun MetroEndIcon() {
    Box(
        modifier = Modifier
            .size(8.dp)
            .clip(CircleShape)
            .border(
                2.dp,
                Color.Red,
                CircleShape
            )
    )
}