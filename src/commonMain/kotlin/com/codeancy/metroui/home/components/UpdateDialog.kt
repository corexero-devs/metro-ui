package com.codeancy.metroui.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codeancy.metroui.common.components.MetroDialog
import com.codeancy.metroui.common.utils.MetroUiColor
import indianmetro.metroui.generated.resources.Res
import indianmetro.metroui.generated.resources.upd_block_hint
import indianmetro.metroui.generated.resources.upd_note
import indianmetro.metroui.generated.resources.upd_primary_hard
import indianmetro.metroui.generated.resources.upd_primary_soft
import indianmetro.metroui.generated.resources.upd_secondary_soft
import indianmetro.metroui.generated.resources.upd_sub
import indianmetro.metroui.generated.resources.upd_title
import org.jetbrains.compose.resources.stringResource

sealed class UpdateKind {
    object Hard : UpdateKind()
    object Soft : UpdateKind()
}

data class UpdateUiModel(
    val kind: UpdateKind,
    val title: String,
    val subtitle: String,
    val note: String,
    val primaryCta: String,
    val secondaryCta: String? = null,
    val showBlockHint: Boolean = false
)

@Composable
fun rememberHardUpdateModel(): UpdateUiModel = UpdateUiModel(
    kind = UpdateKind.Hard,
    title = stringResource(Res.string.upd_title),
    subtitle = stringResource(Res.string.upd_sub),
    note = stringResource(Res.string.upd_note),
    primaryCta = stringResource(Res.string.upd_primary_hard),
    secondaryCta = null,
    showBlockHint = true
)

@Composable
fun rememberSoftUpdateModel(): UpdateUiModel = UpdateUiModel(
    kind = UpdateKind.Soft,
    title = stringResource(Res.string.upd_title),
    subtitle = stringResource(Res.string.upd_sub),
    note = stringResource(Res.string.upd_note),
    primaryCta = stringResource(Res.string.upd_primary_soft),
    secondaryCta = stringResource(Res.string.upd_secondary_soft),
    showBlockHint = false
)

@Composable
fun UpdateDialog(
    model: UpdateUiModel,
    onPrimary: () -> Unit,
    modifier: Modifier = Modifier,
    onSecondary: (() -> Unit)? = null,
    onDismissRequest: (() -> Unit)? = null
) {
    MetroDialog(
        title = model.title,
        modifier = modifier,
        onClose = {
            onDismissRequest?.invoke() ?: Unit
        }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                model.subtitle,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
            )

            Spacer(Modifier.height(16.dp))

            Surface(
                color = MaterialTheme.colorScheme.error.copy(alpha = 0.06f),
                shape = RoundedCornerShape(12.dp),
            ) {
                Text(
                    model.note,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.padding(12.dp)
                )
            }

            if (model.showBlockHint) {
                Spacer(Modifier.height(8.dp))
                Text(
                    stringResource(Res.string.upd_block_hint),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 14.sp,
                        color = MetroUiColor.onSurface.copy(alpha = 0.6f),
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                if (model.kind is UpdateKind.Soft && model.secondaryCta != null) {
                    OutlinedButton(
                        onClick = { onSecondary?.invoke() },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, Color.Black)
                    ) {
                        Text(
                            text = model.secondaryCta,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 14.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.SemiBold,
                            )
                        )
                    }
                }


                Button(
                    onClick = onPrimary,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError
                    )
                ) {
                    Text(
                        text = model.primaryCta,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 14.sp,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold,
                        )
                    )
                }

            }
        }
    }
}