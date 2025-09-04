package com.codeancy.metroui.common.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.codeancy.metroui.common.utils.MetroUiColor

@Composable
fun HomeScreenComponentHeader(
    title: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = title,
            style = TextStyle(
                color = MetroUiColor.onBackground,
                fontWeight = FontWeight.SemiBold,
                fontSize = 17.sp,
            )
        )
    }
}