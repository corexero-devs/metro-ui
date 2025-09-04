package com.codeancy.metroui.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.codeancy.metroui.common.utils.MetroConfig
import com.codeancy.metroui.home.utils.HomeScreenUiAction
import indianmetro.metroui.generated.resources.Res
import indianmetro.metroui.generated.resources.ic_chat_bubble
import indianmetro.metroui.generated.resources.share_feedback_description
import indianmetro.metroui.generated.resources.share_feedback_subtitle
import indianmetro.metroui.generated.resources.share_feedback_title
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedBackCard(
    modifier: Modifier = Modifier,
    onSubmitFeedback: (HomeScreenUiAction.OnSubmitFeedback) -> Unit
) {

    var isFeedbackShown by remember { mutableStateOf(false) }

    val gradient = Brush.linearGradient(
        listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.primary)
    )

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)


    if (isFeedbackShown) {
        ModalBottomSheet(
            onDismissRequest = { isFeedbackShown = false },
            sheetState = sheetState,
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
        ) {
            val metroConfigCurrent = MetroConfig
            UserFeedBackForm(
                onSubmit = { rating, topics, feedback, email ->
                    onSubmitFeedback(
                        HomeScreenUiAction.OnSubmitFeedback(
                            rating = rating,
                            topics = topics,
                            feedback = feedback,
                            email = email,
                        )
                    )
                    isFeedbackShown = false
                },
                onCancel = { isFeedbackShown = false }
            )
        }
    }

    Column(
        modifier = modifier
            .background(gradient, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .clickable { isFeedbackShown = !isFeedbackShown }
            .padding(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_chat_bubble),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(Modifier.width(16.dp))

            Column(Modifier.weight(1f)) {
                Text(
                    text = stringResource(Res.string.share_feedback_title),
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = stringResource(Res.string.share_feedback_subtitle),
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(Modifier.height(6.dp))

            Row {
                repeat(0) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        tint = Color.Yellow,
                        contentDescription = null,
                        modifier = Modifier
                            .size(18.dp)
                            .padding(end = 2.dp)
                    )
                }
            }

            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(36.dp)
            )

        }

        Spacer(Modifier.height(8.dp))

        Text(
            text = stringResource(Res.string.share_feedback_description),
            color = Color.White.copy(alpha = 0.9f),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
