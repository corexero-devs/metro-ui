package org.corexero.metroui.presentation.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jaipurmetro.metroui.generated.resources.Res
import jaipurmetro.metroui.generated.resources.disclaimer
import jaipurmetro.metroui.generated.resources.love
import org.corexero.metroui.ui.MetroDataProvider
import org.corexero.metroui.ui.theme.interFont
import org.corexero.metroui.ui.theme.subHeadingTitle
import org.jetbrains.compose.resources.stringResource

@Composable
fun Disclaimer(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {

        Text(
            text = stringResource(Res.string.disclaimer),
            style = TextStyle(
                fontFamily = interFont,
                color = subHeadingTitle,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = MetroDataProvider.current.metroBrandConfig.disclaimer,
            style = TextStyle(
                fontFamily = interFont,
                color = subHeadingTitle,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(Res.string.love),
            style = TextStyle(
                fontFamily = interFont,
                color = subHeadingTitle,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier
                .fillMaxWidth()
        )

    }

}