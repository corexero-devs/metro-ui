package com.codeancy.metroui.common.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import indianmetro.metroui.generated.resources.Res
import indianmetro.metroui.generated.resources.metro_route
import org.jetbrains.compose.resources.stringResource

@Composable
fun ProgressDialog(
    isLoading: Boolean,
    onDismissRequest: () -> Unit
) {
    if (isLoading) {
        MetroDialog(
            title = stringResource(Res.string.metro_route),
            onClose = onDismissRequest
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .heightIn(
                        min = 100.dp
                    ),
            ) {
                CircularProgressIndicator()
            }
        }
    }
}