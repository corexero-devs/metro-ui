package com.codeancy.metroui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codeancy.metroui.common.utils.MetroConfig
import indianmetro.metroui.generated.resources.Res
import indianmetro.metroui.generated.resources.ic_premium
import indianmetro.metroui.generated.resources.metro
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HomeScreenHeader(
    title: String,
    modifier: Modifier = Modifier,
    onPremiumClicked: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.primary)
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
            text = title,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
        )
        Spacer(modifier = Modifier.weight(1f))

        if (!MetroConfig.isPremiumUser) {
            Icon(
                imageVector = vectorResource(Res.drawable.ic_premium),
                contentDescription = null,
                tint = Color.Yellow,
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        onPremiumClicked()
                    }
            )
        }

    }
}

@Preview
@Composable
fun HomeScreenHeaderPreview() {
    HomeScreenHeader("Metro UI")
}
