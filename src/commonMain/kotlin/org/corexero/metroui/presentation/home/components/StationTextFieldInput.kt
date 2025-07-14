package org.corexero.metroui.presentation.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jaipurmetro.metroui.generated.resources.Res
import jaipurmetro.metroui.generated.resources.cross
import org.corexero.metroui.ui.theme.greenDotColor
import org.corexero.metroui.ui.theme.interFont
import org.corexero.metroui.ui.theme.redDotColor
import org.jetbrains.compose.resources.vectorResource

private val indicatorWidth = 2.dp

@Composable
fun StationTextFieldInput(
    value: String,
    isSource: Boolean,
    onValueChange: (String) -> Unit,
    suggestions: List<String>,
    hintText: String,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    var isFocused by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
    ) {
        val borderColor by animateColorAsState(
            if (isFocused) MaterialTheme.colors.primary else Color(0xFFD0D7DE)
        )

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .height(IntrinsicSize.Max)
                .onFocusChanged {
                    isFocused = it.isFocused
                },
            textStyle = MaterialTheme.typography.h5.copy(
                color = Color.Black,
                fontFamily = interFont,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = if (isSource) ImeAction.Next else ImeAction.Done
            ),
            cursorBrush = Brush.linearGradient(
                listOf(
                    MaterialTheme.colors.primary,
                    MaterialTheme.colors.primary
                )
            ),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFFF0F4F8))
                        .border(
                            2.dp,
                            borderColor,
                            RoundedCornerShape(10.dp)
                        )
                        .padding(horizontal = 16.dp)
                        .height(IntrinsicSize.Max),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StationSelectionLeading(isSource)
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(IntrinsicSize.Max)
                            .padding(
                                vertical = 14.dp,
                                horizontal = 12.dp
                            ),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (value.isEmpty()) {
                            Text(
                                text = hintText,
                                style = MaterialTheme.typography.h5.copy(
                                    color = Color.Gray,
                                    fontFamily = interFont,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 14.sp
                                )
                            )
                        }
                        innerTextField()
                    }
                    if (value.isNotEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .clip(CircleShape)
                                .clickable {
                                    onValueChange("")
                                    if (!isFocused) {
                                        focusRequester.requestFocus()
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = vectorResource(Res.drawable.cross),
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier
                                    .size(18.dp)
                            )
                        }
                    }
                }
            }
        )

        val filteredSuggestions = remember(value, suggestions) {
            fuzzyMatchSubstring(value, suggestions)
        }

        AnimatedVisibility(isFocused && filteredSuggestions.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 200.dp)
                    .verticalScroll(rememberScrollState()),
            ) {
                filteredSuggestions.forEach { suggestion ->
                    DropdownMenuItem(
                        onClick = {
                            onValueChange(suggestion)
                            if (isSource) {
                                focusManager.moveFocus(FocusDirection.Next)
                            } else {
                                focusManager.clearFocus(true)
                            }
                        }
                    ) {
                        Text(suggestion)
                    }
                }
            }
        }
        AnimatedVisibility(isSource && (filteredSuggestions.isEmpty() || !isFocused)) {
            Box(
                modifier = Modifier
                    .padding(start = 20.dp)
                    .height(24.dp)
                    .width(indicatorWidth)
                    .background(Color(0xFFD1D5DB))
            )
        }
    }
}

@Composable
private fun Dot(
    isSource: Boolean,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .size(10.dp)
            .clip(CircleShape)
            .background(if (isSource) greenDotColor else redDotColor)
    )

}

@Composable
private fun StationSelectionLeading(isSource: Boolean) {
    var halfBoxHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .heightIn(min = 32.dp)
            .onGloballyPositioned {
                halfBoxHeight = with(density) {
                    (it.size.height / 2).toDp()
                }
            },
    ) {
        if (isSource) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .height(halfBoxHeight + 5.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Dot(true)
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .width(indicatorWidth)
                        .background(Color(0xFFD1D5DB))
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .height(halfBoxHeight + 5.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .width(indicatorWidth)
                        .background(Color(0xFFD1D5DB))
                )
                Dot(
                    false
                )
            }
        }
    }
}

fun fuzzyMatchSubstring(
    input: String,
    stations: List<String>,
    maxResult: Int = 10
): List<String> {
    if (input.isEmpty()) return listOf()

    val results = LinkedHashSet<String>()
    val inputLower = input.lowercase()

    for (inputLength in inputLower.length downTo 1) {
        val substrings = generateSubstrings(inputLower, inputLength)

        for (substring in substrings) {
            for (station in stations) {
                if (station.lowercase().contains(substring)) {
                    results.add(station)
                    if (results.size == maxResult) {
                        return results.toList()
                    }
                }
            }
        }
    }

    return results.toList()
}

fun generateSubstrings(input: String, length: Int): List<String> {
    val substrings = mutableListOf<String>()
    for (i in 0..(input.length - length)) {
        substrings.add(input.substring(i, i + length))
    }
    return substrings
}