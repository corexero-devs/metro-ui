package com.codeancy.metroui.inAppReview

import androidx.compose.runtime.Composable

@Composable
expect fun rememberShowInAppReview() : ShowInAppReview

interface ShowInAppReview {
    fun show()
}
