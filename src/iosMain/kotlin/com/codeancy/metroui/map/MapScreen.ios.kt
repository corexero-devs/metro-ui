package com.codeancy.metroui.map

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.codeancy.metroui.common.utils.MapDrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
actual fun MapImage(
    modifier: Modifier,
    mapDrawableResource: MapDrawableResource,
    contentDescription: String?,
    contentScale: ContentScale
) {
    Image(
        painter = painterResource(mapDrawableResource),
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale
    )
}