package org.corexero.metroui.presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.corexero.metroui.ui.theme.subHeadingTitle

@Composable
fun MetroStopInBetween(
    color: Color = Color.Gray
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
    ) {
        Row(
            modifier = Modifier
                .width(20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .height(40.dp)
                    .padding(vertical = 4.dp)
                    .background(color, RoundedCornerShape(20.dp))
                    .width(2.dp)
            )
        }
        Box(
            modifier = Modifier
                .padding(start = 32.dp, end = 24.dp)
                .width(1.dp)
                .background(subHeadingTitle)
                .fillMaxHeight()
        )
    }
}