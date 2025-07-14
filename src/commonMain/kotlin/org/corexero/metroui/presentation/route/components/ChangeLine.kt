package org.corexero.metroui.presentation.route.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jaipurmetro.metroui.generated.resources.Res
import jaipurmetro.metroui.generated.resources.change_here
import jaipurmetro.metroui.generated.resources.swap
import org.corexero.metroui.ui.theme.interFont
import org.corexero.metroui.ui.theme.subHeadingTitle
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun ChangeLine(
    lineName: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = vectorResource(Res.drawable.swap),
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .rotate(90f),
            tint = subHeadingTitle
        )

        Text(
            text = stringResource(Res.string.change_here, lineName),
            style = TextStyle(
                fontFamily = interFont,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = subHeadingTitle
            )
        )
    }
}