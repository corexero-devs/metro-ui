package org.corexero.metroui.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jaipurmetro.metroui.generated.resources.Res
import jaipurmetro.metroui.generated.resources.metro
import org.corexero.metroui.ui.MetroDataProvider
import org.corexero.metroui.ui.theme.interFont
import org.jetbrains.compose.resources.vectorResource

@Composable
fun HomeScreenHeader(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(MaterialTheme.colors.primary)
            .padding(
                vertical = 12.dp,
                horizontal = 12.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(
            imageVector = vectorResource(Res.drawable.metro),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(20.dp)
        )
        Text(
            text = MetroDataProvider.current.metroBrandConfig.brandHeaderName,
            style = MaterialTheme.typography.h6.copy(
                fontFamily = interFont,
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}