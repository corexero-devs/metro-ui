package com.codeancy.metroui.common.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import indianmetro.metroui.generated.resources.Res
import indianmetro.metroui.generated.resources.feedback_btn_cancel
import indianmetro.metroui.generated.resources.feedback_btn_submit
import indianmetro.metroui.generated.resources.feedback_cat_design
import indianmetro.metroui.generated.resources.feedback_cat_features
import indianmetro.metroui.generated.resources.feedback_cat_other
import indianmetro.metroui.generated.resources.feedback_cat_perf
import indianmetro.metroui.generated.resources.feedback_email_helper
import indianmetro.metroui.generated.resources.feedback_email_hint
import indianmetro.metroui.generated.resources.feedback_email_q
import indianmetro.metroui.generated.resources.feedback_more_helper
import indianmetro.metroui.generated.resources.feedback_more_hint
import indianmetro.metroui.generated.resources.feedback_more_q
import indianmetro.metroui.generated.resources.feedback_rate_hint
import indianmetro.metroui.generated.resources.feedback_rate_q
import indianmetro.metroui.generated.resources.feedback_subtitle
import indianmetro.metroui.generated.resources.feedback_title
import indianmetro.metroui.generated.resources.feedback_topic_q
import indianmetro.metroui.generated.resources.ic_chat_bubble
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

private val FeedbackPurpleTop @Composable get() = MaterialTheme.colorScheme.primary
private val FeedbackPurpleBottom @Composable get() = MaterialTheme.colorScheme.primary
private val FeedbackStar = Color(0xFFFACC15)

@Composable
fun UserFeedBackForm(
    onSubmit: (rating: Int, topics: Set<String>, feedback: String, email: String) -> Unit,
    onCancel: () -> Unit
) {
    var rating by remember { mutableStateOf(0) }
    var selected by remember { mutableStateOf(setOf<String>()) }
    var feedback by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        listOf(FeedbackPurpleTop, FeedbackPurpleBottom)
                    ),
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                )
                .padding(vertical = 24.dp, horizontal = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(84.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.16f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_chat_bubble),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(36.dp)
                    )
                }
                Spacer(Modifier.height(16.dp))
                Text(
                    text = stringResource(Res.string.feedback_title),
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = stringResource(Res.string.feedback_subtitle), // "Help us make Metro App better for everyone"
                    color = Color.White.copy(alpha = 0.95f),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 20.dp)) {

            Text(
                text = stringResource(Res.string.feedback_rate_q),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                (1..5).forEach { i ->
                    Icon(
                        imageVector = if (i <= rating) Icons.Filled.Star else Icons.Outlined.Star,
                        contentDescription = null,
                        tint = if (i <= rating) FeedbackStar else LocalContentColor.current.copy(
                            alpha = 0.35f
                        ),
                        modifier = Modifier
                            .size(36.dp)
                            .clickable { rating = i }
                            .padding(horizontal = 4.dp)
                    )
                }
            }
            Spacer(Modifier.height(8.dp))
            Text(
                text = stringResource(Res.string.feedback_rate_hint), // "Tap a star to rate"
                style = MaterialTheme.typography.bodyMedium,
                color = LocalContentColor.current.copy(alpha = 0.6f),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(Modifier.height(28.dp))

            Text(
                text = stringResource(Res.string.feedback_topic_q),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(12.dp))

            FeedbackOptionsGrid(
                options = listOf(
                    stringResource(Res.string.feedback_cat_design) to Icons.Filled.ChatBubble,
                    stringResource(Res.string.feedback_cat_features) to Icons.Filled.Bolt,
                    stringResource(Res.string.feedback_cat_perf) to Icons.Filled.TrendingUp,
                    stringResource(Res.string.feedback_cat_other) to Icons.Filled.HelpOutline
                ),
                selected = selected,
                onToggle = { label ->
                    selected = if (label in selected) selected - label else selected + label
                }
            )

            Spacer(Modifier.height(28.dp))

            Text(
                text = stringResource(Res.string.feedback_more_q),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = feedback,
                onValueChange = { feedback = it },
                placeholder = { Text(stringResource(Res.string.feedback_more_hint)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                minLines = 4
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = stringResource(Res.string.feedback_more_helper), // "Your feedback helps us improve…"
                style = MaterialTheme.typography.bodyMedium,
                color = LocalContentColor.current.copy(alpha = 0.6f)
            )

            Spacer(Modifier.height(24.dp))

            Text(
                text = stringResource(Res.string.feedback_email_q),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text(stringResource(Res.string.feedback_email_hint)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                singleLine = true
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = stringResource(Res.string.feedback_email_helper), // "We'll only contact you…"
                style = MaterialTheme.typography.bodyMedium,
                color = LocalContentColor.current.copy(alpha = 0.6f)
            )

            Spacer(Modifier.height(28.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = onCancel,
                    modifier = Modifier.weight(1f).height(56.dp),
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.dp, Color(0xFFDADDE1)),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF111827)),
                ) {
                    Text(
                        stringResource(Res.string.feedback_btn_cancel),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Button(
                    onClick = { onSubmit(rating, selected, feedback, email) },
                    modifier = Modifier.weight(1f).height(56.dp),
                    shape = RoundedCornerShape(14.dp),
                    enabled = feedback.isNotBlank(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = FeedbackPurpleTop,
                        contentColor = Color.White,
                        disabledContainerColor = Color(0xFFE5E5EA),
                        disabledContentColor = Color.White.copy(alpha = 0.75f)
                    )
                ) {
                    Text(
                        stringResource(Res.string.feedback_btn_submit),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
private fun FeedbackOptionsGrid(
    options: List<Pair<String, ImageVector>>,
    selected: Set<String>,
    onToggle: (String) -> Unit
) {
    val hGap = 16.dp
    BoxWithConstraints(Modifier.fillMaxWidth()) {
        val tileW = (maxWidth - hGap) / 2

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(hGap),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            options.forEach { (label, icon) ->
                FeedbackOptionCard(
                    label = label,
                    icon = icon,
                    selected = label in selected,
                    onClick = { onToggle(label) },
                    modifier = Modifier.width(tileW)
                )
            }
        }
    }
}


@Composable
private fun FeedbackOptionCard(
    label: String,
    icon: ImageVector,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val border = if (selected) FeedbackPurpleTop else Color(0xFFE5E7EB)
    val bg = if (selected) FeedbackPurpleTop.copy(.04f) else Color.White

    Surface(
        modifier = modifier
            .height(116.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        color = bg,
        border = BorderStroke(1.dp, border),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                Modifier.size(36.dp).clip(CircleShape)
                    .background(FeedbackPurpleTop.copy(.16f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    icon, null, tint = FeedbackPurpleTop,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(Modifier.height(14.dp))
            Text(
                label,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF111827)
            )
        }
    }
}
