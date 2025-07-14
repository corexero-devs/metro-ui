package org.corexero.metroui.presentation.route.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jaipurmetro.metroui.generated.resources.Res
import jaipurmetro.metroui.generated.resources.metro_route
import org.corexero.metroui.ui.theme.interFont
import org.jetbrains.compose.resources.stringResource

@Composable
fun MetroRouteHeader(
    onBack: () -> Unit,
    onShare: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(MaterialTheme.colors.primary)
            .padding(
                vertical = 8.dp,
                horizontal = 12.dp
            ),
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .clickable { onBack() }
                .size(28.dp)
                .align(Alignment.CenterStart)
        )

        Text(
            text = stringResource(Res.string.metro_route),
            style = MaterialTheme.typography.h6.copy(
                fontFamily = interFont,
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
            ),
            modifier = Modifier
                .align(Alignment.Center)
        )

        Icon(
            imageVector = Icons.Default.Share,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .clickable { onShare() }
                .size(24.dp)
                .align(Alignment.CenterEnd)
        )
    }
}