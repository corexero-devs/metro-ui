package com.codeancy.metroui.map

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.codeancy.metroui.common.utils.MapDrawableResource

@Composable
actual fun MapImage(
    modifier: Modifier,
    mapDrawableResource: MapDrawableResource,
    contentDescription: String?,
    contentScale: ContentScale
) {
    Image(
        painter = painterResource(mapDrawableResource.resId),
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier
    )
}