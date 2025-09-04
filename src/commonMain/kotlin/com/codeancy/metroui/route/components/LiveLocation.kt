package com.codeancy.metroui.route.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.codeancy.metroui.common.utils.MetroUiColor
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
fun LiveLocation(
    isLiveUpdate: Boolean,
    hasLocation: Boolean,
    modifier: Modifier = Modifier
) {
    AnimatedContent(
        isLiveUpdate,
        modifier = modifier
    ) {

        val color by rememberInfiniteTransition()
            .animateColor(
                initialValue = if (!hasLocation) {
                    MetroUiColor.subHeading
                } else MetroUiColor.liveLocation.liveColor,
                targetValue = if (!hasLocation) {
                    MetroUiColor.subHeading.copy(alpha = 0.5f)
                } else MetroUiColor.liveLocation.liveColor50,
                label = "Live Location Space Animation",
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 300,
                        delayMillis = 0
                    ),
                    repeatMode = RepeatMode.Reverse
                )
            )

        if (it) {
            Box(
                modifier = Modifier
                    .border(
                        2.dp,
                        if (!hasLocation) {
                            MetroUiColor.subHeading
                        } else {
                            MetroUiColor.liveLocation.liveColor
                        },
                        CircleShape
                    )
                    .padding(3.dp)
                    .clip(CircleShape)
                    .background(color)
            )
        } else {
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .border(
                        1.dp,
                        MetroUiColor.subHeading,
                        CircleShape
                    )
                    .size(12.dp)
                    .background(MetroUiColor.subHeading)
            )
        }
    }

}